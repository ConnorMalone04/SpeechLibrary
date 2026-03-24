package Project1.SpeechLibrary.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "title") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDir,
                       Model model) {

        String actualSortField = getActualSortField(sortField);

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(actualSortField).ascending()
                : Sort.by(actualSortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Speech> speechPage = speechRepository.findAll(pageable);

        model.addAttribute("speeches", speechPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", speechPage.getTotalPages());
        model.addAttribute("totalItems", speechPage.getTotalElements());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("isSearch", false);

        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(defaultValue = "title") String sortField,
                         @RequestParam(defaultValue = "asc") String sortDir,
                         Model model) {

        String actualSortField = getActualSortField(sortField);

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(actualSortField).ascending()
                : Sort.by(actualSortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Speech> speechPage = speechRepository.searchAll(query, pageable);

        model.addAttribute("speeches", speechPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", speechPage.getTotalPages());
        model.addAttribute("totalItems", speechPage.getTotalElements());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("query", query);
        model.addAttribute("isSearch", true);

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/speeches/{id}")
    public String viewSpeech(@PathVariable Long id, Model model) {
        Speech speech = speechRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid speech id"));

        model.addAttribute("speech", speech);
        return "speech";
    }

    private String getActualSortField(String sortField) {
        return switch (sortField) {
            case "person" -> "person.name";
            case "topic" -> "topic.name";
            default -> "title";
        };
    }
}