package sapp.service;

import org.springframework.core.io.Resource;

public interface AvatarService {

	Resource getCurrentPicturePath();

	Resource getAvatarResourceByUsername(String username);

}