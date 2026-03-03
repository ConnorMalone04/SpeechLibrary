package Project1.SpeechLibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Project1.SpeechLibrary.data.SpeechRepository;
import Project1.SpeechLibrary.model.Speech;

@Controller
public class HomeController {

    private final SpeechRepository speechRepository;

    public HomeController(SpeechRepository speechRepository) {
        this.speechRepository = speechRepository;
    }

    // home page: list speeches
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("speeches",
                speechRepository.findAllByOrderByTitleAsc());
        return "home";
    }

    // view speech
    @GetMapping("/speeches/{id}")
    public String viewSpeech(@PathVariable Long id, Model model) {

        Speech speech = speechRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid speech id"));

        model.addAttribute("speech", speech);
        return "speech";
    }
}