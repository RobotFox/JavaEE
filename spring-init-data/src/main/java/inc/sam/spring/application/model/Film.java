package inc.sam.spring.application.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Film {

	@Id
	private Short film_id;
	private String title;
	private String description;

	public Film() {
	}

	public Film(Short film_id) {
		super();
		this.film_id = film_id;
	}

	public Short getFilm_id() {
		return film_id;
	}

	public void setFilm_id(Short film_id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((film_id == null) ? 0 : film_id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (film_id == null) {
			if (other.film_id != null)
				return false;
		} else if (!film_id.equals(other.film_id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Film [film_id=" + film_id + ", title=" + title + ", description=" + description + "]";
	}

}
