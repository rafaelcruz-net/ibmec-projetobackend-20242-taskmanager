package br.edu.ibmec.projeto.backend.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ibmec.projeto.backend.task_manager.model.Task;
import br.edu.ibmec.projeto.backend.task_manager.model.User;
import br.edu.ibmec.projeto.backend.task_manager.repository.UserRepository;
import ch.qos.logback.core.testUtil.RandomUtil;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping()
    public String index(Model modelView) {
        // Obtendo todos os usuários
        modelView.addAttribute("listaUser", repository.findAll());
        return "lista-user";
    }

    @GetMapping("/create")
    public String createUser() {
        return "criar-user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {
        // Inserção no banco de dados
        repository.save(user); 

        return "redirect:/user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        Optional<User> optUser = repository.findById(id);

        if (optUser.isPresent()) {
            repository.delete(optUser.get());
        }

        return "redirect:/user";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model modelAndView, @PathVariable("id") Integer id) {
        Optional<User> optUser = repository.findById(id);

        modelAndView.addAttribute("editUser", optUser.get());
        return "editar-user";
    }

    @PostMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") Integer id, @ModelAttribute User newData) {
        Optional<User> optUser = repository.findById(id);

        User user = optUser.get();

        user.setName(newData.getName());

        repository.save(user);
        
        return "redirect:/user";
    }



}
