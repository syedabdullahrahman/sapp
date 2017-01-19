package sapp.controller.chat;

import java.io.Serializable;

public class InitMessage implements Serializable{

	private static final long serialVersionUID = 7635015732736552784L;
	private String name;
	
    public InitMessage() {
	}

	public InitMessage(String name) {
        this.name = name;
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

}
