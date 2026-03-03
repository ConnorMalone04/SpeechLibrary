package Project1.SpeechLibrary.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import Project1.SpeechLibrary.data.UserRepository;
import Project1.SpeechLibrary.model.User;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadUsers(UserRepository userRepository) {
        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(new User("admin", "admin123", "ADMIN"));
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                userRepository.save(new User("user", "user123", "CUSTOMER"));
            }
        };
    }
}