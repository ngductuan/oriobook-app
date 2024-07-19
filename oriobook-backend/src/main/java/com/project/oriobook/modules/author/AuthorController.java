package com.project.oriobook.modules.author;

import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.author.dto.FindAllAuthorQueryDTO;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.author.services.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    public PageResponse<Author> getAllAuthors(@ParameterObject @ModelAttribute FindAllAuthorQueryDTO query) {
        Page<Author> authorsList = authorService.getAllAuthors(query);

        return new PageResponse<>(authorsList);
    }

    // @PostMapping("")
    // @PreAuthorize(RoleConst.ROLE_ADMIN)
    // @ResponseStatus(HttpStatus.CREATED)
    // public Boolean createAuthor(@Valid @RequestBody AuthorDTO authorDTO, BindingResult result) throws Exception {
    //     if(result.hasErrors()) {
    //         throw new ValidationException(result);
    //     }
    //
    //     Author newAuthor = authorService.createAuthor(authorDTO);
    //     return newAuthor != null;
    // }

    //
    // @PutMapping("/{id}")
    // @PreAuthorize(RoleConst.ROLE_ADMIN)
    // @ResponseStatus(HttpStatus.OK)
    // public Boolean updateAuthor(@PathVariable String id, @Valid @RequestBody AuthorDTO authorDTO, BindingResult result) throws Exception {
    //     if(result.hasErrors()) {
    //         throw new ValidationException(result);
    //     }
    //
    //     Author updatedAuthor = authorService.updateAuthor(id, authorDTO);
    //     return updatedAuthor != null;
    // }
}
