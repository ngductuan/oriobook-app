package com.project.oriobook.modules.author.services;

import com.project.oriobook.common.exceptions.AuthorException;
import com.project.oriobook.common.utils.PaginationUtil;
import com.project.oriobook.modules.author.dto.CreateAuthorDTO;
import com.project.oriobook.modules.author.dto.FindAllAuthorQueryDTO;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService{
    private final AuthorRepository authorRepository;

    private final ModelMapper modelMapper;

    @Override
    public Page<Author> getAllAuthors(FindAllAuthorQueryDTO query) {
        if(query == null || query.isGetAll()){
            return authorRepository.findAll(new FindAllAuthorQueryDTO(), Pageable.unpaged());
        }

        PageRequest pageRequest = PaginationUtil.generatePageRequest(query, new ArrayList<>());
        Page<Author> authorPaging = authorRepository.findAll(query, pageRequest);

        return authorPaging;
    }

    @Override
    public Author getAuthorById(String id) throws Exception {
        Author optionalAuthor = authorRepository.findById(id)
                .orElseThrow(AuthorException.NotFound::new);
        return optionalAuthor;
    }

    @Override
    @Transactional
    public Author createAuthor(CreateAuthorDTO authorDTO) throws Exception {
        modelMapper.typeMap(CreateAuthorDTO.class, Author.class);
        Author author = modelMapper.map(authorDTO, Author.class);
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author updateAuthor(String id, CreateAuthorDTO authorDTO) throws Exception {
        Author author = authorRepository.findById(id)
                .orElseThrow(AuthorException.NotFound::new);
        modelMapper.typeMap(CreateAuthorDTO.class, Author.class);
        modelMapper.map(authorDTO, author);
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(String id) throws Exception {
        Author author = authorRepository.findById(id)
                .orElseThrow(AuthorException.NotFound::new);
        authorRepository.delete(author);
    }

    @Override
    @Transactional
    public Boolean adjustPublishedBooks(String id, int publishedBooks) throws Exception {
        if(publishedBooks < 0){
            throw new AuthorException.InvalidBookQuantity();
        }
        Author author = authorRepository.findById(id)
                .orElseThrow(AuthorException.NotFound::new);

        return authorRepository.setPublishedBooks(id, publishedBooks) != 0;
    }
}
