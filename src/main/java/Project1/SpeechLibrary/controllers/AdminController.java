package Project1.SpeechLibrary.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Project1.SpeechLibrary.data.PersonRepository;
import Project1.SpeechLibrary.data.SpeechRepository;
import Project1.SpeechLibrary.data.TopicRepository;
import Project1.SpeechLibrary.model.Person;
import Project1.SpeechLibrary.model.Speech;
import Project1.SpeechLibrary.model.Topic;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final TopicRepository topicRepository;
    private final PersonRepository personRepository;
    private final SpeechRepository speechRepository;

    public AdminController(
            TopicRepository topicRepository,
            PersonRepository personRepository,
            SpeechRepository speechRepository) {
        this.topicRepository = topicRepository;
        this.personRepository = personRepository;
        this.speechRepository = speechRepository;
    }

/* --------------------------------------------------------------
                            DASHBOARD 
-------------------------------------------------------------- */ 
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String adminDashboard(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                 Model model) {
        model.addAttribute("username", user.getUsername());
        return "admin/dashboard";
    }

/* --------------------------------------------------------------
                            TOPICS 
-------------------------------------------------------------- */    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/topics")
    public String manageTopics(Model model) {
        model.addAttribute("topics", topicRepository.findAll());
        return "admin/topics";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/topics/new")
    public String newTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        return "admin/topic-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/topics/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid topic id"));
        model.addAttribute("topic", topic);
        return "admin/topic-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/topics/save")
    public String saveTopic(Topic topic, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/topic-form";
        }
        topicRepository.save(topic);
        return "redirect:/admin/topics";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/topics/delete/{id}")
    public String deleteTopic(@PathVariable Long id) {
        topicRepository.deleteById(id);
        return "redirect:/admin/topics";
    }


/* --------------------------------------------------------------
                            PEOPLE 
-------------------------------------------------------------- */ 
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/people")
    public String managePeople(Model model) {
        model.addAttribute("people", personRepository.findAll());
        return "admin/people";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/people/new")
    public String newPersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "admin/person-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/people/edit/{id}")
    public String editPerson(@PathVariable Long id, Model model) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person id"));
        model.addAttribute("person", person);
        return "admin/person-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/people/save")
    public String savePerson(Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/person-form";
        }
        personRepository.save(person);
        return "redirect:/admin/people";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/people/delete/{id}")
    public String deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return "redirect:/admin/people";
    }

/* --------------------------------------------------------------
                            SPEECHES 
-------------------------------------------------------------- */ 
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/speeches")
    public String manageSpeeches(Model model) {
        model.addAttribute("speeches", speechRepository.findAll());
        return "admin/speeches";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/speeches/new")
    public String newSpeechForm(Model model) {
        model.addAttribute("speech", new Speech());
        model.addAttribute("people", personRepository.findAll());
        model.addAttribute("topics", topicRepository.findAll());
        return "admin/speech-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/speeches/edit/{id}")
    public String editSpeech(@PathVariable Long id, Model model) {
        Speech speech = speechRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid speech id"));
        model.addAttribute("speech", speech);
        model.addAttribute("people", personRepository.findAll());
        model.addAttribute("topics", topicRepository.findAll());
        return "admin/speech-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/speeches/save")
    public String saveSpeech(Speech speech, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("people", personRepository.findAll());
            model.addAttribute("topics", topicRepository.findAll());
            return "admin/speech-form";
        }
        speechRepository.save(speech);
        return "redirect:/admin/speeches";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/speeches/delete/{id}")
    public String deleteSpeech(@PathVariable Long id) {
        speechRepository.deleteById(id);
        return "redirect:/admin/speeches";
    }

}