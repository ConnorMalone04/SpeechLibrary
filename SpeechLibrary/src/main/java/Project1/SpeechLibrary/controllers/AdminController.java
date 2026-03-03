package Project1.SpeechLibrary.controllers;

import java.util.List;

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
import Project1.SpeechLibrary.data.UserRepository;
import Project1.SpeechLibrary.model.Person;
import Project1.SpeechLibrary.model.Speech;
import Project1.SpeechLibrary.model.Topic;
import Project1.SpeechLibrary.model.User;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final TopicRepository topicRepository;
    private final PersonRepository personRepository;
    private final SpeechRepository speechRepository;
    private final UserRepository userRepository;

    public AdminController(
            TopicRepository topicRepository,
            PersonRepository personRepository,
            SpeechRepository speechRepository,
            UserRepository userRepository) {

        this.topicRepository = topicRepository;
        this.personRepository = personRepository;
        this.speechRepository = speechRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String adminDashboard(HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        return "admin/dashboard";
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.getRole().equals("ADMIN");
    }

/* --------------------------------------------------------------
                            TOPICS 
-------------------------------------------------------------- */

    // manage topics (list of topics page)
    @GetMapping("/topics")
    public String manageTopics(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("topics", topicRepository.findAll());
        return "admin/topics";
    }

    // create new topic (empty form)
    @GetMapping("/topics/new")
    public String newTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        return "admin/topic-form";
    }

    // edit existing topic (form pre-filled with topic data)
    @GetMapping("/topics/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid topic id"));

        model.addAttribute("topic", topic);
        return "admin/topic-form";
    }

    // save topic (handle form submission for both create and edit)
    @PostMapping("/topics/save")
    public String saveTopic(Topic topic, BindingResult bindingResult, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "admin/topic-form";
        }

        topicRepository.save(topic);
        return "redirect:/admin/topics";
    }

    // delete topic (handle delete action from edit page)
    @PostMapping("/topics/delete/{id}")
    public String deleteTopic(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        topicRepository.deleteById(id);
        return "redirect:/admin/topics";
    }

/* --------------------------------------------------------------
                            PEOPLE 
-------------------------------------------------------------- */
    // manage people (list page)
    @GetMapping("/people")
    public String managePeople(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("people", personRepository.findAll());
        return "admin/people";
    }

    // create new person (empty form)
    @GetMapping("/people/new")
    public String newPersonForm(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        model.addAttribute("person", new Person());
        return "admin/person-form";
    }

    // edit existing person
    @GetMapping("/people/edit/{id}")
    public String editPerson(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person id"));

        model.addAttribute("person", person);
        return "admin/person-form";
    }

    // save person (create + edit)
    @PostMapping("/people/save")
    public String savePerson(Person person,
            BindingResult bindingResult,
            HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "admin/person-form";
        }

        personRepository.save(person);
        return "redirect:/admin/people";
    }

    // delete person
    @PostMapping("/people/delete/{id}")
    public String deletePerson(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        personRepository.deleteById(id);
        return "redirect:/admin/people";
    }

/* --------------------------------------------------------------
                            SPEECHES 
-------------------------------------------------------------- */
    // manage speeches (list page)
    @GetMapping("/speeches")
    public String manageSpeeches(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("speeches", speechRepository.findAll());
        return "admin/speeches";
    }

    // create new speech
    @GetMapping("/speeches/new")
    public String newSpeechForm(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("speech", new Speech());
        model.addAttribute("people", personRepository.findAll());

        return "admin/speech-form";
    }

    // edit existing speech
    @GetMapping("/speeches/edit/{id}")
    public String editSpeech(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        Speech speech = speechRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid speech id"));

        model.addAttribute("speech", speech);
        model.addAttribute("people", personRepository.findAll());

        return "admin/speech-form";
    }

    // save speech
    @PostMapping("/speeches/save")
    public String saveSpeech(Speech speech,
                            BindingResult bindingResult,
                            Model model,
                            HttpSession session) {

        if (!isAdmin(session)) return "redirect:/login";

        if (bindingResult.hasErrors()) {
            model.addAttribute("people", personRepository.findAll());
            return "admin/speech-form";
        }

        speechRepository.save(speech);
        return "redirect:/admin/speeches";
    }

    // delete speech
    @PostMapping("/speeches/delete/{id}")
    public String deleteSpeech(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        speechRepository.deleteById(id);
        return "redirect:/admin/speeches";
    }

    // manage users
    @GetMapping("/users")
    public String manageUsers(Model model, HttpSession session) {

        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }
}
