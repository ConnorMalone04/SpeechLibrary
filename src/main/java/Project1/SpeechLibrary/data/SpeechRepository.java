package Project1.SpeechLibrary.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Project1.SpeechLibrary.model.Speech;

public interface SpeechRepository extends JpaRepository<Speech, Long> {

    @Query("""
        SELECT DISTINCT s
        FROM Speech s
        LEFT JOIN s.person p
        LEFT JOIN s.topics t
        WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    Page<Speech> searchAll(String query, Pageable pageable);
}