package sapp.controller.chat;

public class ChatUser {
String name;
String avatarPath;


public ChatUser(String name, String avatarPath) {
	this.name = name;
	this.avatarPath = avatarPath;
}

public ChatUser() {
}

/**
 * @return the name
 */
public String getName() {
	return name;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

/**
 * @return the avatarPath
 */
public String getAvatarPath() {
	return avatarPath;
}

/**
 * @param avatarPath the avatarPath to set
 */
public void setAvatarPath(String avatarPath) {
	this.avatarPath = avatarPath;
}




}
