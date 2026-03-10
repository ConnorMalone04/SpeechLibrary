package Project1.SpeechLibrary.controllers;

import java.util.List;

import org.springframework.data.domain.Sort;
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
    public String home(@RequestParam(defaultValue = "title") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       Model model) {
    
        String actualSortField;
    
        switch (sortField) {
            case "person":
                actualSortField = "person.name";
                break;
            case "topic":
                actualSortField = "topic.name";
                break;
            default:
                actualSortField = "title";
        }
    
        Sort sort = sortDir.equals("asc")
                ? Sort.by(actualSortField).ascending()
                : Sort.by(actualSortField).descending();
    
        List<Speech> speeches = speechRepository.findAll(sort);
    
        model.addAttribute("speeches", speeches);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("isSearch", false);

    
        return "home";
    }

    // search function
    @GetMapping("/search")
    public String search(@RequestParam String query,
                        @RequestParam(defaultValue = "title") String sortField,
                        @RequestParam(defaultValue = "asc") String sortDir,
                        Model model) {

        String actualSortField;
    
        switch (sortField) {
            case "person":
                actualSortField = "person.name";
                break;
            case "topic":
                actualSortField = "topic.name";
                break;
            default:
                actualSortField = "title";
        }

        Sort sort = sortDir.equals("asc")
                ? Sort.by(actualSortField).ascending()
                : Sort.by(actualSortField).descending();

        model.addAttribute("speeches", speechRepository.searchAll(query, sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("query", query);
        model.addAttribute("isSearch", true);

        return "home";
    }

    // login page
    @GetMapping("/login")
    public String login() {
        return "login";
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