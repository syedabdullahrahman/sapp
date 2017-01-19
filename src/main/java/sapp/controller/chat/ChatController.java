package sapp.controller.chat;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sapp.model.User;
import sapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
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
	
	@Autowired
    ServletContext context;

	private ArrayList<ChatUser> users;
	
	
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
		
		
		User modelUser = userService.findByUsername(message.getName());
		String path;
		if( modelUser.getAvatarPath() ==  null || modelUser.getAvatarPath().isEmpty()){
			path = "/images/profile/anonymous.png";
		}else{
			Resource picturePath = (new DefaultResourceLoader()).getResource("file:./" + modelUser.getAvatarPath());
			/images/profile --> upload here thanks to the "get real path" <- change in upload properties the path	
			path = context.getRealPath(picturePath.getURI().toString());??
		}
		
		
		users.add(new ChatUser(message.getName(),path));
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






