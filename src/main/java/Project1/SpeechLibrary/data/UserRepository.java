package Project1.SpeechLibrary.data;

import org.springframework.data.jpa.repository.JpaRepository;
import Project1.SpeechLibrary.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}