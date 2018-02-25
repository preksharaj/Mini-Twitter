

package com.example.challenge.VO;

public class Messages {
	private String user_handle; // ID of the user who sent the message
	private String message; // Content of the message

	/**
	* Constructors
	*/
	public Messages() {

	}

	public Messages(String user_handle, String message) {
		this.user_handle = user_handle;
		this.message = message;
	}

	public String getUserHandle() {
		return user_handle;
	}

	
	public void setUserHandle(String user_handle) {
		this.user_handle = user_handle;
	}

	
	public String getMessage() {
		return message;
	}

	
	public void setMessage(String message) {
		this.message = message;
	}
}