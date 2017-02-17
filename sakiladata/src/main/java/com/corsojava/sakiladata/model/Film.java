package com.corsojava.sakiladata.model;

import java.util.List;

public class Film {

	private int film_id;
	private String title;
	private String description;
	private String release_year;
	private Language language_id;
	private Language original_language_id;
	private List<Actor> actors;
	private int length;

	public int getFilm_id() {
		return film_id;
	}

	public void setFilm_id(int film_id) {
		this.film_id = film_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRelease_year() {
		return release_year;
	}

	public void setRelease_year(String release_year) {
		this.release_year = release_year;
	}

	public Language getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Language language_id) {
		this.language_id = language_id;
	}

	public Language getOriginal_language_id() {
		return original_language_id;
	}

	public void setOriginal_language_id(Language original_language_id) {
		this.original_language_id = original_language_id;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Film withId(int i) {
		this.setFilm_id(i);
		return this;
	}

	public Film withTitle(String string1) {
		this.setTitle(string1);
		return this;

	}

	public Film withDescription(String string) {
		this.setDescription(string);
		return this;
	}

	public Film withRelease_Year(String string) {
		this.setRelease_year(string);
		return this;
	}

	public Film withLanguage(int int1, String name) {
		this.language_id = new Language().withId(int1).withName(name);
		return this;
	}

	public Film withOriginal_Language(Integer int1) {
		if (int1 == null) {
			this.setOriginal_language_id(getLanguage_id());
		}
		return this;
	}

	public Film withLength(int int1) {
		this.setLength(int1);
		return this;
	}

	public Film withActors(List<Actor> ac) {
		this.setActors(ac);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + film_id;
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
		Film other = (Film) obj;
		if (film_id != other.film_id)
			return false;
		return true;
	}

}
