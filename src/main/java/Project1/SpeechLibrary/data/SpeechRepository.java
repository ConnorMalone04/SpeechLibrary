package Project1.SpeechLibrary.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Project1.SpeechLibrary.model.Speech;

public interface SpeechRepository extends JpaRepository<Speech, Long> {
    
    List<Speech> findAllByOrderByTitleAsc();
}