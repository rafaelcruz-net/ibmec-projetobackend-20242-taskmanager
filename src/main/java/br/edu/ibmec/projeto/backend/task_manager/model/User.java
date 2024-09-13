package br.edu.ibmec.projeto.backend.task_manager.model;

import java.time.LocalDate;

import lombok.Data;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotEmpty(message = "Campo nome não pode ser vazio")
    private String name;

    @OneToMany
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private List<Task> tasks;
}
