package com.project.oriobook.modules.author.services;

import com.project.oriobook.common.exceptions.AuthorException;
import com.project.oriobook.common.utils.QueryUtil;
import com.project.oriobook.modules.author.dto.AuthorDTO;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService{
    private final AuthorRepository authorRepository;

    private final ModelMapper modelMapper;

    @Override
    public Page<Author> getAllAuthors(FindAllAuthorQueryDTO query) {
        if(query == null){
            return authorRepository.findAll(new FindAllAuthorQueryDTO(), Pageable.unpaged());
        }

        List<Sort.Order> orders = QueryUtil.parseSortBase(query);

        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by(orders));
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
    public Author createAuthor(AuthorDTO authorDTO) throws Exception {
        modelMapper.typeMap(AuthorDTO.class, Author.class);
        Author author = modelMapper.map(authorDTO, Author.class);
        return authorRepository.save(author);
    }
}
