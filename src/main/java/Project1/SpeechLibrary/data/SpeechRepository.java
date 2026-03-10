package Project1.SpeechLibrary.data;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Project1.SpeechLibrary.model.Speech;

public interface SpeechRepository extends JpaRepository<Speech, Long> {
    
    @Query("""
        SELECT s
        FROM Speech s
        LEFT JOIN s.person p
        LEFT JOIN s.topic t
        WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<Speech> searchAll(String query, Sort sort);
    List<Speech> findAllByOrderByTitleAsc();
}