package com.project.oriobook.modules.category.repository;

import com.project.oriobook.modules.category.dto.FindAllCategoryQueryDTO;
import com.project.oriobook.modules.category.entities.Category;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("SELECT p FROM Category p WHERE " +
            "p.isMain = true AND " +
            "(:#{#query.categoryName} IS NULL OR :#{#query.categoryName} = '' OR p.name LIKE %:#{#query.categoryName}%) " +
            "AND (:#{#query.startDate} IS NULL OR p.createdAt >= :#{#query.startDate}) " +
            "AND (:#{#query.endDate} IS NULL OR p.createdAt <= :#{#query.endDate})")
    Page<Category> findAllMain(@Param("query") FindAllCategoryQueryDTO query, Pageable pageable);
    boolean existsByName(String name);
}
