package com.project.oriobook.modules.category.services;

import com.project.oriobook.modules.category.entities.Category;

public interface ICategoryService {
    Category getCategoryById(String id) throws Exception;
}
