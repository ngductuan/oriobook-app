package com.project.oriobook.modules.category.repository;

import com.project.oriobook.modules.category.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {

}
