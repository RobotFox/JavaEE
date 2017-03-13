package inc.sam.spring.application.repositories;

import org.springframework.data.repository.CrudRepository;

import inc.sam.spring.application.model.Film;

public interface FilmRepository extends CrudRepository<Film, Short> {

}
