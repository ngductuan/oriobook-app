package com.project.oriobook.modules.author.services;

import com.project.oriobook.modules.author.dto.CreateAuthorDTO;
import com.project.oriobook.modules.author.dto.FindAllAuthorQueryDTO;
import com.project.oriobook.modules.author.entities.Author;
import org.springframework.data.domain.Page;

public interface IAuthorService {
    Page<Author> getAllAuthors(FindAllAuthorQueryDTO query);
    Author getAuthorById(String id) throws Exception;
    Author createAuthor(CreateAuthorDTO authorDTO) throws Exception;
    Author updateAuthor(String id, CreateAuthorDTO authorDTO) throws Exception;
    void deleteAuthor(String id) throws Exception;
    Boolean adjustPublishedBooks(String id, int publishedBooks) throws Exception;
}
