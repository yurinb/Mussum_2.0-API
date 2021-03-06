package com.mussum.repository;

import com.mussum.models.db.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    @Query(value = "select p from Professor p where p.username=:pusername")
    public Professor findByUsername(@Param("pusername") String username);
    
}
