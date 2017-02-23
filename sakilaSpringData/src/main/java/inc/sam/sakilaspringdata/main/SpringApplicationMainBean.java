package inc.sam.sakilaspringdata.main;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import inc.sam.sakilaspringdata.jpa.ActorRepository;
import inc.sam.sakilaspringdata.model.Actor;

public class SpringApplicationMainBean {

	@Autowired
	private ActorRepository repository;

	public List<Actor> getAllActors() {
		List<Actor> actors = new LinkedList<Actor>();
		for (Actor actor : repository.findAll()) {
			actors.add(actor);
		}
		return actors;

	}

}
