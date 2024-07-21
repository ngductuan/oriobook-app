package com.project.oriobook.modules.category;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.category.dto.CreateMainCategoryDTO;
import com.project.oriobook.modules.category.dto.CreateSubCategoryDTO;
import com.project.oriobook.modules.category.dto.FindAllCategoryQueryDTO;
import com.project.oriobook.modules.category.entities.Category;
import com.project.oriobook.modules.category.responses.CategoryResponse;
import com.project.oriobook.modules.category.services.CategoryService;
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
@Tag(name = "categories")
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<CategoryResponse> getAllMainCategories(@ParameterObject @ModelAttribute FindAllCategoryQueryDTO query) {
        Page<Category> categoriesList = categoryService.getAllMainCategories(query);

        modelMapper.typeMap(Category.class, CategoryResponse.class);
        Page<CategoryResponse> categoryResponse = categoriesList.map(product ->
                modelMapper.map(product, CategoryResponse.class));

        return new PageResponse<>(categoryResponse);
    }

    @PostMapping("/main")
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean createMainCategory(@Valid @RequestBody CreateMainCategoryDTO categoryDTO, BindingResult result)
            throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Category newCategory = categoryService.createMainCategory(categoryDTO);
        return newCategory != null;
    }

    @PutMapping("/main/{id}")
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean updateMainCategory(@PathVariable String id, @Valid @RequestBody CreateMainCategoryDTO categoryDTO,
                                      BindingResult result) throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Category newCategory = categoryService.updateMainCategory(id, categoryDTO);
        return newCategory != null;
    }


    @PostMapping("/sub")
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean createSubCategory(@Valid @RequestBody CreateSubCategoryDTO categoryDTO, BindingResult result)
            throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Category newCategory = categoryService.createSubCategory(categoryDTO);
        return newCategory != null;
    }

    @PutMapping("/sub/{id}")
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean updateSubCategory(@PathVariable String id, @Valid @RequestBody CreateSubCategoryDTO categoryDTO,
                                     BindingResult result) throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Category newCategory = categoryService.updateSubCategory(id, categoryDTO);
        return newCategory != null;
    }

    // @DeleteMapping("/{id}")
    // @SecurityRequirement(name = CommonConst.BEARER_KEY)
    // @PreAuthorize(RoleConst.ROLE_ADMIN)
    // @ResponseStatus(HttpStatus.OK)
    // public Boolean deleteCategory(@PathVariable String id) throws Exception {
    //     return categoryService.deleteCategory(id);
    // }
}
