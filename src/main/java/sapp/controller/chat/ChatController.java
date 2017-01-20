package sapp.controller.chat;

import java.security.Principal;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import org.h2.engine.SysProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
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
	private SortedSet<String> users;
	
	public ChatController(){
		this.users=new TreeSet<>();
		this.users.add("ChatBot");
	}
	
	@RequestMapping("/chat")	
	public String showChat(Model model) {
		model.addAttribute("name", userService.getPrincipalName());
		return "chat";
	}
	
	@MessageMapping("/login")
	public void login(Principal principal) throws Exception {
		users.add(principal.getName());
		messaging.convertAndSend("/topic/users", new UserListMessage(users));
		messaging.convertAndSend("/topic/messages", 
				new ChatMessage("SERVER",principal.getName()+" joined!"));
		messaging.convertAndSendToUser(principal.getName(), "/topic/priv",
				new ChatMessage("SERVER","Be good " + principal.getName()+"!"));
	}
	@MessageMapping("/disconnect")
	public void disconnect(Principal principal) throws Exception {
		System.out.println("removing user: " + users.remove(principal.getName()));
		System.out.println("bye bye " + principal.getName());
	}
	
	@MessageMapping("/messages")
    public void message(ChatMessage message, Principal principal) throws Exception {
		messaging.convertAndSend("/topic/messages", 
				new ChatMessage(message.getSender(),message.getContent())
				);
    System.out.println("FROM " + principal.getName());
	}
		
}






