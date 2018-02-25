

package com.example.challenge.VO;

public class Followers {
	private String user_handle; // handle of the user
	private String follower_handle; // handle of the follower

	/**
	* Consructors
	*/
	public Followers() {

	}

	public Followers(String user_handle, String follower_handle) {
		this.user_handle = user_handle;
		this.follower_handle = follower_handle;
	}

	public String getUserHandle() {
		return user_handle;
	}

	public void setUserHandle(String user_handle) {
		this.user_handle = user_handle;
	}

	public String getFollowerHandle() {
		return follower_handle;
	}

	public void setFollowerHandle(String follower_handle) {
		this.follower_handle = follower_handle;
	}
}
