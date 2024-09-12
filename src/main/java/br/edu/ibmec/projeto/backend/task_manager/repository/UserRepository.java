package br.edu.ibmec.projeto.backend.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ibmec.projeto.backend.task_manager.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
