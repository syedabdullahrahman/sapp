package sapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import sapp.config.UploadProperties;
import sapp.model.User;
import sapp.service.UserService;

@Service
public class AvatarServiceImpl implements AvatarService {
	private final Resource anonymousPicture;
	private final Resource chatBotPicture;
	
	@Autowired UserService userService;
	
    @Autowired
    public AvatarServiceImpl(UploadProperties uploadProperties) {
        anonymousPicture = uploadProperties.getAnonymousPicture();
        chatBotPicture = uploadProperties.getChatBotPicture();
    }
    
    @Override
	public Resource getCurrentPicturePath(){
    	Resource picturePath;
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			User modelUser = userService.findByUsername(auth.getName());
			if( modelUser.getAvatarPath() ==  null || modelUser.getAvatarPath().isEmpty()){
				picturePath = anonymousPicture;
			}else{
				picturePath = (new DefaultResourceLoader()).getResource("file:./" + modelUser.getAvatarPath());
			}
		}else{
			picturePath = anonymousPicture;
		}
    	
    	return picturePath;
    }
    
	@Override
	@Cacheable("avatar")
	public Resource getAvatarResourceByUsername(String username){
		if(username.equals("ChatBot")){
			return chatBotPicture;
		}
		Resource picturePath;
		User modelUser = userService.findByUsername(username);
		if( modelUser.getAvatarPath() ==  null || modelUser.getAvatarPath().isEmpty()){
			picturePath = anonymousPicture;
		}else{
			picturePath = (new DefaultResourceLoader()).getResource("file:./" + modelUser.getAvatarPath());
		}
		return picturePath;
	}
    
 
}
