
package com.example.challenge.VO;

public class People {
	private int people_id; // ID of the user
	private String handle; // Username
	private String name;//Full name of User
    

	/**
    *Constructors
    */
    
	public People() {
	}
    
	public People(int people_id, String handle, String name) {
		this.people_id = people_id;
		this.handle = handle;
		this.name = name;
	}

	public int getId() {
		return people_id;
	}

	public void setId(int id) {
		this.people_id = id;
	}


	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
