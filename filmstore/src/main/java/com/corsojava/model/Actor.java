package com.corsojava.model;

public class Actor {

	private int id;
	private String firstName;
	private String lastName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Actor withId(int int1) {
		this.setId(int1);
		return this;
	}

	public Actor withFirstName(String string) {
		this.setFirstName(string);
		return this;
	}

	public Actor withLastName(String string) {
		this.setLastName(string);
		return this;
	}

}
