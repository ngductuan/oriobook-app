package com.project.oriobook.modules.product.services;

import com.project.oriobook.modules.product.dto.CreateProductDTO;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import org.springframework.data.domain.Page;

public interface IProductService {
    Page<Product> getAllProducts(FindAllProductQueryDTO query);
    Product getProductById(String id) throws Exception;
    Product createProduct(CreateProductDTO createProductDTO) throws Exception;
    Product updateProduct(String id, CreateProductDTO updateProductDTO) throws Exception;
    void deleteProduct(String id) throws Exception;
}
