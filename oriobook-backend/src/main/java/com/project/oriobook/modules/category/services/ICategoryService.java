package com.project.oriobook.modules.category.services;

import com.project.oriobook.modules.category.dto.CreateMainCategoryDTO;
import com.project.oriobook.modules.category.dto.CreateSubCategoryDTO;
import com.project.oriobook.modules.category.dto.FindAllCategoryQueryDTO;
import com.project.oriobook.modules.category.entities.Category;
import org.springframework.data.domain.Page;

public interface ICategoryService {
    Page<Category> getAllMainCategories(FindAllCategoryQueryDTO query);
    Category getCategoryById(String id) throws Exception;
    Category createMainCategory(CreateMainCategoryDTO category) throws Exception;
    Category updateMainCategory(String id, CreateMainCategoryDTO category) throws Exception;
    Category createSubCategory(CreateSubCategoryDTO category) throws Exception;
    Category updateSubCategory(String id, CreateSubCategoryDTO category) throws Exception;
    void deleteCategory(String id) throws Exception;

    boolean isExistingByName(String name) throws Exception;
}
