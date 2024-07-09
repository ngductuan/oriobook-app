package com.project.oriobook.modules.product.repository;

import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:#{#query.productName} IS NULL OR :#{#query.productName} = '' OR p.name = :#{#query.productName}) " +
            "AND (:#{#query.categoryId} IS NULL OR :#{#query.categoryId} = '' OR p.categoryNode.id = :#{#query.categoryId})")
    Page<Product> findAll(@Param("query") FindAllProductQueryDTO query, Pageable pageable);
}
