package br.edu.ibmec.projeto.backend.task_manager.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    @NotEmpty(message = "Campo nome da tarefa não pode ser vazio")
    private String name;
    
    @Column
    @NotEmpty(message = "Campo status da tarefa não pode ser vazio")
    private String status;
    
    @Column
    private LocalDate createdDate;

}
