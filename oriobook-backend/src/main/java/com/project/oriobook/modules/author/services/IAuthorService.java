package com.project.oriobook.modules.author.services;

import com.project.oriobook.modules.author.dto.AuthorDTO;
import com.project.oriobook.modules.author.dto.FindAllAuthorQueryDTO;
import com.project.oriobook.modules.author.entities.Author;
import org.springframework.data.domain.Page;

public interface IAuthorService {
    Page<Author> getAllAuthors(FindAllAuthorQueryDTO query);
    Author getAuthorById(String id) throws Exception;
    Author createAuthor(AuthorDTO authorDTO) throws Exception;
}
