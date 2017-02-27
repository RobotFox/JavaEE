package inc.sam.sakilaspringdata.main;

import org.springframework.beans.factory.annotation.Autowired;

import inc.sam.sakilaspringdata.jpa.ActorRepository;
import inc.sam.sakilaspringdata.model.Actor;

public class SpringApplicationMainBean {

	@Autowired
	private ActorRepository repository;

	public Iterable<Actor> getAllActors() {
		return repository.findAll();
	}

}
