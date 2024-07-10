package com.project.oriobook.modules.category.repository;

import com.project.oriobook.modules.category.entities.Category;
import com.project.oriobook.modules.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

}
