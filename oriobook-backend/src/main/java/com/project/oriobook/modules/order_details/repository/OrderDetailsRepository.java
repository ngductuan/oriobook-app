package com.project.oriobook.modules.order_details.repository;

import com.project.oriobook.modules.order_details.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String>{

}
