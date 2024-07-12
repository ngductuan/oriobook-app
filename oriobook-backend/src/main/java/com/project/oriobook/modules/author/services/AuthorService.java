package com.project.oriobook.modules.author.services;

import com.project.oriobook.common.exceptions.AuthorException;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService{
    private final AuthorRepository authorRepository;

    @Override
    public Author getAuthorById(String id) throws Exception {
        Author optionalAuthor = authorRepository.findById(id)
                .orElseThrow(AuthorException.NotFound::new);
        return optionalAuthor;
    }
}
