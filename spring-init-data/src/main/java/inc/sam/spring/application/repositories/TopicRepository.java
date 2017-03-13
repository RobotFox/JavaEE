package inc.sam.spring.application.repositories;

import org.springframework.data.repository.CrudRepository;

import inc.sam.spring.application.model.Topic;

public interface TopicRepository extends CrudRepository<Topic, String>{
	
	
}
