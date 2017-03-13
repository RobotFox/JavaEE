package inc.sam.spring.application.business.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sam.spring.application.model.Course;
import inc.sam.spring.application.model.Topic;
import inc.sam.spring.application.repositories.CourseRepository;
import inc.sam.spring.application.repositories.TopicRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public List<Course> getAllCourses(String topicId) {
		List<Course> courses = new LinkedList<>();
		courseRepository.findByTopicId(topicId).forEach(courses::add);
		return courses;
	}

	public Course getCourse(String id) {
		// return topics.stream().filter(t ->
		// t.getId().equals(id)).findFirst().get();
		return courseRepository.findOne(id);
	}

	public void addCourse(Course course) {
		courseRepository.save(course);
	}

	public void updateCourse(Course course) {
		courseRepository.save(course);
	}

	public void deleteCourse(String id) {
		// topics.removeIf(t -> t.getId().equals(id));
		courseRepository.delete(id);
	}

}
