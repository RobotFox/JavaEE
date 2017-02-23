package com.corsojava.filmstorejpa.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "actor")
public class Actor implements Serializable {

	@Id
	@Column(name = "actor_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long actor_id;

	@Column(name = "first_name")
	@Basic(optional = false)
	private String first_name;

	@Column(name = "last_name")
	@Basic(optional = false)
	private String last_name;

	protected Actor() {
	}

	public Actor(String first_name, String last_name) {
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public Long getActor_id() {
		return actor_id;
	}

	public void setActor_id(Long actor_id) {
		this.actor_id = actor_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actor_id == null) ? 0 : actor_id.hashCode());
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
		if (actor_id == null) {
			if (other.actor_id != null)
				return false;
		} else if (!actor_id.equals(other.actor_id))
			return false;
		return true;
	}
	
	

}
