package com.project.oriobook.modules.product.services;

import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.dto.ProductDTO;
import com.project.oriobook.modules.product.entities.Product;
import org.springframework.data.domain.Page;

public interface IProductService {
    Page<Product> getAllProducts(FindAllProductQueryDTO query);
    Product createProduct(ProductDTO createProductDTO) throws Exception;
    Product updateProduct(String id, ProductDTO updateProductDTO) throws Exception;
    void deleteProduct(String id) throws Exception;
}
