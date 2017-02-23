package inc.sam.sakilaspringdata.jpa;

import org.springframework.data.repository.CrudRepository;

import inc.sam.sakilaspringdata.model.Actor;

public interface ActorRepository extends CrudRepository<Actor, Integer> {

}
