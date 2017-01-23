package sapp.controller.chat;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.scheduling.annotation.Scheduled;
import sapp.service.UserService;

@Controller
public class ChatController {
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SimpMessageSendingOperations messaging;
	@Autowired 
	private UserService userService;
	@Autowired ChatBot chatBot;
	private SortedSet<String> users;
	private SortedSet<String> pongUsers;
	private String chatBotName;
	private List<String> matches;
	private Matcher matcher;
	
	public ChatController(){
		this.matches = new ArrayList<>();
		this.chatBotName = "ChatBot";
		this.users=new TreeSet<>();
		this.pongUsers = new TreeSet<>();
		this.users.add(this.chatBotName);
	}
	
	/**
	 * @return the users
	 */
	public SortedSet<String> getUsers() {
		return users;
	}

	/**
	 * show chat view
	 * @param model
	 * @return
	 */
	@RequestMapping("/chat")	
	public String showChat(Model model) {
		model.addAttribute("name", userService.getPrincipalName());
		return "chat";
	}
	
	/**
	 * login
	 * @param principal
	 * @throws Exception
	 */
	@MessageMapping("/login")
	public void login(Principal principal) throws Exception {
		// add user
		users.add(principal.getName());
		// send user list
		messaging.convertAndSend("/topic/users", new UserListMessage(users));
		// send joined info
		messaging.convertAndSend("/topic/messages", 
				new ChatMessage(chatBotName,principal.getName()+" joined!"));
		// priv welcome by bot
		messaging.convertAndSendToUser(principal.getName(), "/topic/priv",
				new ChatMessage(chatBotName,"Be good " + principal.getName()+"!"));
	}
	
	
	private void sendUsers(){
		messaging.convertAndSend("/topic/users", new UserListMessage(users));
	}
	
	/**
	 * logout
	 * @param principal
	 * @throws Exception
	 */
	@MessageMapping("/disconnect")
	public void disconnect(Principal principal) throws Exception {
		// remove from list
		users.remove(principal.getName());
		// send user list
		sendUsers();
	}
	
	/**
	 * handle message
	 * @param message
	 * @param principal
	 * @throws Exception
	 */
	@MessageMapping("/postmessage")
    public void message(ChatMessage message, Principal principal) throws Exception {
		boolean chatBotQuestion = false;
		matches.clear();
		// pattern from Username qualifier
		matcher = Pattern.compile("@[a-zA-Z0-9_-]{2,25}").matcher(message.getContent());
		while (matcher.find()) {
			 matches.add(matcher.group().replace("@",""));
		}
		if(matches.size()>0){
			matches.add(principal.getName());
			for(String s: matches){
				if (s.equals("ChatBot")) {
					chatBotQuestion = true;
				}
				else{
					messaging.convertAndSendToUser(s, "/topic/priv",
							new ChatMessage(message.getSender(),Jsoup.parse(message.getContent()).text()));
				}
			 }
			if (chatBotQuestion){
				Runnable task = new Runnable(){
					  public void run(){
					    try{
					      Thread.sleep(3500) ;
							messaging.convertAndSendToUser(principal.getName(), "/topic/priv",
									new ChatMessage(chatBotName,chatBot.askChatBot(message.getContent()))
							);
					    } catch (Exception ex) { /* … */ }
					  }
					};
					task.run();
			}
		}
		else{
			messaging.convertAndSend("/topic/messages", 
					new ChatMessage(message.getSender(),Jsoup.parse(message.getContent()).text()));
	
		}		
	}
	
	@Scheduled(fixedDelay = 13000)
	private void userPinger() throws InterruptedException{
		// clear pong list
		this.pongUsers.clear();
		// ping all users != ChatBot
		for(String s: this.users){
			if(s.equals(this.chatBotName)){
				this.pongUsers.add(this.chatBotName);
			}
			messaging.convertAndSendToUser(s, "/topic/ping","");
		}
		// wait for response (to gather in pongUsers)
		Thread.sleep(2000);
		// swap lists
		this.users = this.pongUsers;
		this.pongUsers = new TreeSet<>();
		
		// send users
		sendUsers();

	}
	
	@MessageMapping("/pong")
	public void pong(Principal principal) throws Exception {
		this.pongUsers.add(principal.getName());
	}
	
		

}