package inc.sam.spring.application.business.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sam.spring.application.model.Topic;
import inc.sam.spring.application.repositories.TopicRepository;

@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;

	public List<Topic> getAllTopics() {
		List<Topic> topics = new LinkedList<>();
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}

	public Topic getTopic(String id) {
		//return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
		return topicRepository.findOne(id);
	}

	public void addTopic(Topic topic) {
		topicRepository.save(topic);
	}

	public void updateTopic(String id, Topic topic) {
		topicRepository.save(topic);
	}

	public void deleteTopic(String id) {
		//topics.removeIf(t -> t.getId().equals(id));
		topicRepository.delete(id);
	}

}
