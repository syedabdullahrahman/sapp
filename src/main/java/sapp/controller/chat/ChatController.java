package sapp.controller.chat;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Controller
public class ChatController {
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SimpMessageSendingOperations messaging;
	@Autowired 
	private UserService userService;
	private ArrayList<String> users;
	
	public ChatController(){
		this.users=new ArrayList<>();
	}
	
	@RequestMapping("/chat")	
	public String showChat(Model model) {
		model.addAttribute("name", userService.getPrincipalName());
		return "chat";
	}
	
	@MessageMapping("/login")
    public void login(InitMessage message) throws Exception {
		messaging.convertAndSend("/topic/messages", 
				new ChatMessage("SERVER",message.getName()+" joined!"));
		users.add(message.getName());
		messaging.convertAndSend("/topic/users",
				new UserListMessage(users));
    }
	
	@MessageMapping("/messages")
	@SendTo("/topic/chatroom")
    public void message(ChatMessage message) throws Exception {
		messaging.convertAndSend("/topic/messages", 
				new ChatMessage(message.getSender(),message.getContent())
				);
    }
}






