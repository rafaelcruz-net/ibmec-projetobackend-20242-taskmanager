package br.edu.ibmec.projeto.backend.task_manager.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import br.edu.ibmec.projeto.backend.task_manager.model.Task;
import br.edu.ibmec.projeto.backend.task_manager.model.User;
import br.edu.ibmec.projeto.backend.task_manager.repository.TaskRepository;
import br.edu.ibmec.projeto.backend.task_manager.repository.UserRepository;
import ch.qos.logback.core.testUtil.RandomUtil;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/task/{id_user}")
public class TaskController {
    
    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String index(Model modelView, @PathVariable("id_user") int id_user) {
        //Obtendo todas as tarefas do usuário
        User user = userRepository.findById(id_user).get();

        modelView.addAttribute("id_user", id_user);
        modelView.addAttribute("listaTarefas", user.getTasks());
        return "lista-task";
    }

    @GetMapping("/create")
    public String createTask(@PathVariable("id_user") int id_user, Model modelView, Task task) {
        modelView.addAttribute("id_user", id_user);
        return "criar-task";
    }

    @PostMapping("/saveTask")
    public String saveTask(@PathVariable("id_user") int id_user, @Valid @ModelAttribute Task task, BindingResult result, Model modelView) {

        if (result.hasErrors()) {
            modelView.addAttribute("id_user", id_user);
            return "criar-task";
        }
        
        //Obtendo o usuário a ser adicionado a tarefa
        User user = userRepository.findById(id_user).get();

        //Setando a hora a tarefa
        task.setCreatedDate(LocalDate.now());

        //Adicionando a tarefa na lista do usuário
        user.getTasks().add(task);

        //Adiciona a tarefa no banco de dados
        repository.save(task);

        //Associa a tarefa para o usuário
        userRepository.save(user);
        
        return "redirect:/task/" + user.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Integer id) {
        Optional<Task> optTask = repository.findById(id);

        if (optTask.isPresent()) {
            repository.delete(optTask.get());
        }

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model modelAndView, @PathVariable("id") Integer id) {
        Optional<Task> optTask = repository.findById(id);

        modelAndView.addAttribute("editTask", optTask.get());
        return "editar-task";
    }

    @PostMapping("/editTask/{id}")
    public String editTask(@PathVariable("id") Integer id, @ModelAttribute Task newData, BindingResult result) {
        
        if (result.hasErrors()) {
            return "editar-task";
        }
        
        Optional<Task> optTask = repository.findById(id);

        Task task = optTask.get();

        task.setName(newData.getName());
        task.setStatus(newData.getStatus());

        repository.save(task);
        
        return "redirect:/";
    }
    
    
    
    
    
    
}
