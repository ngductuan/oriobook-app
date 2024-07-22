package com.project.oriobook.modules.product.services;

import com.project.oriobook.common.exceptions.ProductException;
import com.project.oriobook.common.utils.QueryUtil;
import com.project.oriobook.common.utils.ValidationUtil;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.author.services.AuthorService;
import com.project.oriobook.modules.category.entities.Category;
import com.project.oriobook.modules.category.services.CategoryService;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.dto.CreateProductDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    private final ModelMapper modelMapper;

    @Override
    public Page<Product> getAllProducts(FindAllProductQueryDTO query) {
        if (query == null) {
            return productRepository.findAll(new FindAllProductQueryDTO(), Pageable.unpaged());
        }

        List<Sort.Order> orders = QueryUtil.parseSortBase(query);

        if (!ValidationUtil.isNullOrBlankStr(query.getSortByRating())) {
            orders.add(new Sort.Order(Sort.Direction.fromString(query.getSortByRating().toString().toLowerCase()), "rating"));
        }

        if (!ValidationUtil.isNullOrBlankStr(query.getSortByPrice())) {
            orders.add(new Sort.Order(Sort.Direction.fromString(query.getSortByPrice().toString().toLowerCase()), "price"));
        }

        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by(orders));
        Page<Product> productPaging = productRepository.findAll(query, pageRequest);

        return productPaging;
    }

    @Override
    public Product getProductById(String id) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(ProductException.NotFound::new);

        return existingProduct;
    }

    @Override
    @Transactional
    public Product createProduct(CreateProductDTO createProductDTO) throws Exception {
        // Check foreign keys
        Category existingCategory = categoryService.getCategoryById(createProductDTO.getCategoryId());
        Author existingAuthor = authorService.getAuthorById(createProductDTO.getAuthorId());

        // Add new product
        modelMapper.typeMap(CreateProductDTO.class, Product.class);

        Product newProduct = new Product();
        modelMapper.map(createProductDTO, newProduct);

        newProduct.setCategoryNode(existingCategory);
        newProduct.setAuthorNode(existingAuthor);

        // increase book for author
        authorService.adjustPublishedBooks(existingAuthor.getId(),
                existingAuthor.getPublishedBook() + 1);

        return productRepository.save(newProduct);
    }

    @Override
    @Transactional
    public Product updateProduct(String id, CreateProductDTO updateProductDTO) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(ProductException.NotFound::new);

        Category existingCategory = categoryService.getCategoryById(updateProductDTO.getCategoryId());
        Author existingAuthor = authorService.getAuthorById(updateProductDTO.getAuthorId());

        modelMapper.typeMap(CreateProductDTO.class, Product.class);
        modelMapper.map(updateProductDTO, existingProduct);

        existingProduct.setCategoryNode(existingCategory);
        existingProduct.setAuthorNode(existingAuthor);

        return productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(String id) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(ProductException.NotFound::new);

        productRepository.delete(existingProduct);
    }
}