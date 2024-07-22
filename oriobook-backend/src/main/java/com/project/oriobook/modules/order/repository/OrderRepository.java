package com.project.oriobook.modules.order.repository;

import com.project.oriobook.modules.order.dto.FindAllOrderQueryDTO;
import com.project.oriobook.modules.order.entities.Order;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT p FROM Order p WHERE " +
            "(:#{#query.startDate} IS NULL OR p.createdAt >= :#{#query.startDate}) " +
            "AND (:#{#query.endDate} IS NULL OR p.createdAt <= :#{#query.endDate})"
    )
    Page<Order> findAll(@Param("query") FindAllOrderQueryDTO query, Pageable pageable);

    @Query("SELECT p FROM Order p WHERE p.userNode.id = :userId " +
            "AND (:#{#query.startDate} IS NULL OR p.createdAt >= :#{#query.startDate}) " +
            "AND (:#{#query.endDate} IS NULL OR p.createdAt <= :#{#query.endDate})"
    )
    Page<Order> findMyOrder(@Param("userId") String userId, @Param("query") FindAllOrderQueryDTO query,
                            Pageable pageable);
}
