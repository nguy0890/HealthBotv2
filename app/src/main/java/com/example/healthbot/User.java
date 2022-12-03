package com.example.healthbot;

public class User {
	private long id;
	private String user ;
	private long gender;
	private long birth;
	public long getId() {
		return id;
	}

	public User() {}
	public User(String name, int gender, int birth) {
		this.user = name;
		this.gender = gender;
		this.birth = birth;
	}
	public void setId(long id) {
		this.id = id;
	}

    public String getUser() {
        return user;
    }
	public long getGender() {
		return gender;
	}
	public long getBirth() {
		return birth;
	}
    public void setUser(String user) {
        this.user = user;
    }
	public void setGender(int gender) {
		this.gender = gender;
	}
	public void setBirth(int birth) {
		this.birth = birth;
	}
    public String toString () {
		String g;
		if(gender == 0) g = "Female";
		else if(gender == 1) g = "Male";
		else g = "undetermined";
		return user + "  id:" + id+ "  Gender:" + g+ "  Birth Year:" + birth;
	}
}