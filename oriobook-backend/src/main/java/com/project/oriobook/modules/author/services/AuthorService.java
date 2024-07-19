package com.project.oriobook.modules.author.services;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.exceptions.AuthorException;
import com.project.oriobook.common.utils.QueryUtil;
import com.project.oriobook.modules.author.dto.FindAllAuthorQueryDTO;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    public Page<Author> getAllAuthors(FindAllAuthorQueryDTO query) {
        if(query == null){
            return authorRepository.findAll(new FindAllAuthorQueryDTO(), Pageable.unpaged());
        }

        query.setGender(CommonEnum.GenderEnum.MALE);

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
}
