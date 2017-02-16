package com.corsojava.model;

public class Language {

	private int language_id;
	private String name;

	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Language withId(int i) {
		this.setLanguage_id(i);
		return this;
	}

	public Language withName(String string1) {
		this.setName(string1);
		return this;

	}

}
