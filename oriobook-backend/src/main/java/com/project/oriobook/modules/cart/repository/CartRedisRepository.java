package com.project.oriobook.modules.cart.repository;

import com.project.oriobook.modules.cart.entities.CartRedisItem;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.List;

public interface CartRedisRepository extends KeyValueRepository<CartRedisItem, String> {
    List<CartRedisItem> findByIdStartingWith(String prefix);
}
