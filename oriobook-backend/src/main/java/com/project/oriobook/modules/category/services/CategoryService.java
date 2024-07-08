package com.project.oriobook.modules.category.services;

import com.project.oriobook.common.exceptions.CategoryException;
import com.project.oriobook.modules.category.entities.Category;
import com.project.oriobook.modules.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    public Category getCategoryById(String id) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            return optionalCategory.get();
        }
        throw new CategoryException.NotFound();
    }
}
