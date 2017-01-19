package sapp.controller.chat;

import java.io.Serializable;

public class ChatMessage implements Serializable{

	private static final long serialVersionUID = -7757483769735725889L;
	private String content;
    private String sender;

    public ChatMessage() {
    }

    public ChatMessage(String sender, String content) {
        this.content = content;
        this.sender = sender;
    }

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

}
