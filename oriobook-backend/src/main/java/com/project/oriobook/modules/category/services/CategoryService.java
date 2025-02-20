package com.project.oriobook.modules.category.services;

import com.project.oriobook.common.exceptions.CategoryException;
import com.project.oriobook.common.utils.PaginationUtil;
import com.project.oriobook.modules.category.dto.CreateMainCategoryDTO;
import com.project.oriobook.modules.category.dto.CreateSubCategoryDTO;
import com.project.oriobook.modules.category.dto.FindAllCategoryQueryDTO;
import com.project.oriobook.modules.category.entities.Category;
import com.project.oriobook.modules.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Override
    public Page<Category> getAllMainCategories(FindAllCategoryQueryDTO query) {
        if (query == null || query.isGetAll()) {
            return categoryRepository.findAllMain(new FindAllCategoryQueryDTO(), Pageable.unpaged());
        }

        PageRequest pageRequest = PaginationUtil.generatePageRequest(query, new ArrayList<>());
        Page<Category> categoryPaging = categoryRepository.findAllMain(query, pageRequest);

        return categoryPaging;
    }

    @Override
    public Category getCategoryById(String id) throws Exception {
        Category optionalCategory = categoryRepository.findById(id)
                .orElseThrow(CategoryException.NotFound::new);
        return optionalCategory;
    }

    @Override
    @Transactional
    public Category createMainCategory(CreateMainCategoryDTO dto) throws Exception {
        if(isExistingByName(dto.getName())){
            return null;
        }

        Category newCategory = modelMapper.map(dto, Category.class);
        return categoryRepository.save(newCategory);
    }

    @Override
    @Transactional
    public Category updateMainCategory(String id, CreateMainCategoryDTO dto) throws Exception {
        // Check if the category is a main category
        Category optionalCategory = categoryRepository.findById(id)
                .orElseThrow(CategoryException.NotFound::new);

        if(!optionalCategory.getIsMain()){
            throw new CategoryException.InvalidType();
        }

        // Check if the category name already exists
        if(isExistingByName(dto.getName())){
            return null;
        }

        Category categoryToUpdate = getCategoryById(id);

        modelMapper.typeMap(CreateMainCategoryDTO.class, Category.class);
        modelMapper.map(dto, categoryToUpdate);

        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    @Transactional
    public Category createSubCategory(CreateSubCategoryDTO dto) throws Exception {
        // Check if the category is a main category
        Category optionalCategory = categoryRepository.findById(dto.getParentId())
                .orElseThrow(CategoryException.NotFound::new);

        if(!optionalCategory.getIsMain()){
            throw new CategoryException.InvalidType();
        }

        // Check if the category name already exists
        if(isExistingByName(dto.getName())){
            return null;
        }

        Category parentCategory = getCategoryById(dto.getParentId());

        Category newCategory = modelMapper.map(dto, Category.class);
        newCategory.setParentNode(parentCategory);

        return categoryRepository.save(newCategory);
    }

    @Override
    @Transactional
    public Category updateSubCategory(String id, CreateSubCategoryDTO dto) throws Exception {
        // Check if the category is a sub category
        Category optionalCategory = categoryRepository.findById(id)
                .orElseThrow(CategoryException.NotFound::new);

        if(optionalCategory.getIsMain()){
            throw new CategoryException.InvalidType();
        }

        // Check parent category
        if(!optionalCategory.getParentNode().getId().equals(dto.getParentId())){
            throw new CategoryException.InvalidParent();
        }

        // Check if the category name already exists
        if(isExistingByName(dto.getName())){
            return null;
        }

        Category categoryToUpdate = getCategoryById(id);

        modelMapper.typeMap(CreateSubCategoryDTO.class, Category.class);
        modelMapper.map(dto, categoryToUpdate);

        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    @Transactional
    public void deleteCategory(String id) throws Exception {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public boolean isExistingByName(String name) throws Exception{
        boolean existsByName = categoryRepository.existsByName(name);

        if (existsByName) {
            throw new CategoryException.DuplicateName();
        }
        return false;
    }
}
