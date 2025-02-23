package com.project.oriobook.modules.author;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.author.dto.CreateAuthorDTO;
import com.project.oriobook.modules.author.dto.FindAllAuthorQueryDTO;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.author.responses.AuthorResponse;
import com.project.oriobook.modules.author.services.AuthorService;
import com.project.oriobook.modules.author.services.IAuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "authors")
@RequestMapping("${api.prefix}/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<AuthorResponse> getAllAuthors(@ParameterObject @ModelAttribute FindAllAuthorQueryDTO query) {
        Page<Author> authorsList = authorService.getAllAuthors(query);

        modelMapper.typeMap(Author.class, AuthorResponse.class);
        Page<AuthorResponse> authorResponse = authorsList.map(product ->
                modelMapper.map(product, AuthorResponse.class));

        return new PageResponse<>(authorResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Author getAuthorById(@PathVariable String id) throws Exception {
        Author author = authorService.getAuthorById(id);
        return author;
    }

    @PostMapping("")
    @Operation(summary = RoleConst.OP_ADMIN)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean createAuthor(@Valid @RequestBody CreateAuthorDTO authorDTO, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Author newAuthor = authorService.createAuthor(authorDTO);
        return newAuthor != null;
    }

    @PutMapping("/{id}")
    @Operation(summary = RoleConst.OP_ADMIN)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    public Boolean updateAuthor(@PathVariable String id, @Valid @RequestBody CreateAuthorDTO authorDTO,
                                BindingResult result)
            throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Author updatedAuthor = authorService.updateAuthor(id, authorDTO);
        return updatedAuthor != null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = RoleConst.OP_ADMIN)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    public Boolean deleteAuthor(@PathVariable String id) throws Exception {
        authorService.deleteAuthor(id);
        return true;
    }
}
