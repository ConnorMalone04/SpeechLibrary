package Project1.SpeechLibrary.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Project1.SpeechLibrary.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByNameContainingIgnoreCase(String keyword);
}