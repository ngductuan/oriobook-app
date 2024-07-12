package com.project.oriobook.modules.category.services;

import com.project.oriobook.common.exceptions.CategoryException;
import com.project.oriobook.modules.category.entities.Category;
import com.project.oriobook.modules.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    public Category getCategoryById(String id) throws Exception {
        Category optionalCategory = categoryRepository.findById(id)
                .orElseThrow(CategoryException.NotFound::new);
        return optionalCategory;
    }
}
