package com.corsojava.sakiladata.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "actor")
public class Actor implements Serializable {

	@Id
	@Column(name = "actor_id")

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "first_name")
	@Basic(optional = false)
	private String firstName;

	@Column(name = "last_name")
	@Basic(optional = false)
	private String lastName;

	@JoinColumn(name = "id", referencedColumnName = "actor_id")
	@OneToMany(cascade = CascadeType.ALL, targetEntity = FilmActor.class)
	private List<FilmActor> filmActors;

	public List<FilmActor> getFilmActors() {
		return filmActors;
	}

	public void setFilmActors(List<FilmActor> filmActors) {
		this.filmActors = filmActors;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Actor other = (Actor) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
