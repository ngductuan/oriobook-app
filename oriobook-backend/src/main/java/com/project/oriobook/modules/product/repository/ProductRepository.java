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
            "(:#{#query.productName} IS NULL OR :#{#query.productName} = '' OR p.name LIKE %:#{#query.productName}%) " +
            "AND (:#{#query.categoryId} IS NULL OR :#{#query.categoryId} = '' OR p.categoryNode.id = :#{#query.categoryId}) " +
            "AND (:#{#query.authorId} IS NULL OR :#{#query.authorId} = '' OR p.authorNode.id = :#{#query.authorId}) " +
            "AND (:#{#query.startDate} IS NULL OR p.createdAt >= :#{#query.startDate}) " +
            "AND (:#{#query.endDate} IS NULL OR p.createdAt <= :#{#query.endDate})"
    )
    Page<Product> findAll(@Param("query") FindAllProductQueryDTO query, Pageable pageable);
}
