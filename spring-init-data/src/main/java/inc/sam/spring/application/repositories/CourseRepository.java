package inc.sam.spring.application.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import inc.sam.spring.application.model.Course;

public interface CourseRepository extends CrudRepository<Course, String>{
	
	public List<Course> findByTopicId(String topicId);
	
	//Esempio di custom CourseRepository con query per ottenere un elenco di Course filtrati per nome
	//public List<Course> findByName(String name);
	
	//Esempio di custom CourseRepository per filtrare si usa findBy{proprietà della classe} in questo caso si specifica una proprietà ulteriore.
	//public List<Course> findByDescription(String description);
}
