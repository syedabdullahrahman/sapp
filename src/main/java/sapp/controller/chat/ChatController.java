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
	private String chatBotName;
	private List<String> matches;
	private Matcher matcher;
	
	public ChatController(){
		this.matches = new ArrayList<>();
		this.chatBotName = "ChatBot";
		this.users=new TreeSet<>();
		this.users.add("ChatBot");
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
		users.add(principal.getName());
		messaging.convertAndSend("/topic/users", new UserListMessage(users));
		messaging.convertAndSend("/topic/messages", 
				new ChatMessage(chatBotName,principal.getName()+" joined!"));
		messaging.convertAndSendToUser(principal.getName(), "/topic/priv",
				new ChatMessage(chatBotName,"Be good " + principal.getName()+"!"));
	}
	
	/**
	 * logout
	 * @param principal
	 * @throws Exception
	 */
	@MessageMapping("/disconnect")
	public void disconnect(Principal principal) throws Exception {
		users.remove(principal.getName());
		messaging.convertAndSend("/topic/users", new UserListMessage(users));
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
				messaging.convertAndSendToUser(principal.getName(), "/topic/priv",
						new ChatMessage(this.chatBotName,chatBot.askChatBot(message.getContent()))
				);
			}
		}
		else{
			messaging.convertAndSend("/topic/messages", 
					new ChatMessage(message.getSender(),Jsoup.parse(message.getContent()).text()));
	
		}				
	}
		

}