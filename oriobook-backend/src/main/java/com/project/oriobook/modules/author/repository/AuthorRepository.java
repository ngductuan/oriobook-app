package com.project.oriobook.modules.author.repository;

import com.project.oriobook.modules.author.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, String>{

}
