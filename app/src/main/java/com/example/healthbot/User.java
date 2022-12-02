package com.example.healthbot;

public class User {
	private long id;
	private String user ;
	private long gender;
	private long age;
	public long getId() {
		return id;
	}

	public User() {}
	public User(String name, int gender, int age) {
		this.user = name;
		this.gender = gender;
		this.age = age;
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
	public long getAge() {
		return age;
	}
    public void setUser(String user) {
        this.user = user;
    }
	public void setGender(int gender) {
		this.gender = gender;
	}
	public void setAge(int age) {
		this.age = age;
	}
    public String toString () {
		return user + "  id:" + id+ "  G:" + gender+ "  age:" + age;
	}
}