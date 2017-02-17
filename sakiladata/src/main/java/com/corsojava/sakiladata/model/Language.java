package com.corsojava.sakiladata.model;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + language_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		if (language_id != other.language_id)
			return false;
		return true;
	}

}
