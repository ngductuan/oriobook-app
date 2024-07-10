package com.project.oriobook.modules.author.services;

import com.project.oriobook.common.exceptions.CategoryException;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService{
    private final AuthorRepository authorRepository;

    @Override
    public Author getAuthorById(String id) throws Exception {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if(optionalAuthor.isPresent()){
            return optionalAuthor.get();
        }
        throw new CategoryException.NotFound();
    }
}
