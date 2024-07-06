package com.project.oriobook.modules.product.services;

import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;

    @Override
    public PageResponse<Product> getAllProducts(FindAllProductQueryDTO query, Pageable pageable) {
        Page<Product> products = productRepository.findAll(query, pageable);
        return new PageResponse<>(products);
    }
}
