package com.project.oriobook.modules.product.repository;

import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:#{#query.productName} IS NULL OR :#{#query.productName} = 0 OR p.name = :#{#query.productName})")
    Page<Product> findAll(@Param("query") FindAllProductQueryDTO query, Pageable pageable);
}
