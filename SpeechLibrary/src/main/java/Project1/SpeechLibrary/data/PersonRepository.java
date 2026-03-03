package Project1.SpeechLibrary.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Project1.SpeechLibrary.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByNameContainingIgnoreCase(String keyword);
}