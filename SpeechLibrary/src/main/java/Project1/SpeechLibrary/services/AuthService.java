package Project1.SpeechLibrary.services;

import java.util.List;

import org.springframework.stereotype.Service;

import Project1.SpeechLibrary.model.User;

@Service
public class AuthService {

    private final List<User> users = List.of(
            new User("admin", "admin123", "ADMIN"),
            new User("user", "user123", "CUSTOMER")
    );

    public User authenticate(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username)
                        && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}