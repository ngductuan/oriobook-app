package com.project.oriobook.modules.product.services;

import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.CreateProductDTO;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import org.springframework.data.domain.*;

public interface IProductService {
    PageResponse<Product> getAllProducts(FindAllProductQueryDTO query);
    Product createProduct(CreateProductDTO createProductDTO) throws Exception;
}
