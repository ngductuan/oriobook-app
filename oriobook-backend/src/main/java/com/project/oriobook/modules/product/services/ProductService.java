package com.project.oriobook.modules.product.services;

import com.project.oriobook.common.helpers.QueryHelper;
import com.project.oriobook.common.utils.ValidationUtil;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.author.services.AuthorService;
import com.project.oriobook.modules.category.entities.Category;
import com.project.oriobook.modules.category.services.CategoryService;
import com.project.oriobook.modules.product.dto.CreateProductDTO;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    private final ModelMapper modelMapper;

    @Override
    public Page<Product> getAllProducts(FindAllProductQueryDTO query) {
        List<Sort.Order> orders = QueryHelper.parseSortBase(query);

        if(ValidationUtil.isNullOrBlank(query.getSortByRating())){
            orders.add(new Sort.Order(Sort.Direction.fromString(query.getSortByRating().toString().toLowerCase()), "rating"));
        }

        if(ValidationUtil.isNullOrBlank(query.getSortByPrice())){
            orders.add(new Sort.Order(Sort.Direction.fromString(query.getSortByPrice().toString().toLowerCase()), "price"));
        }

        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by(orders));
        Page<Product> productPaging = productRepository.findAll(query, pageRequest);

        return productPaging;
    }

    @Override
    @Transactional
    public Product createProduct(CreateProductDTO createProductDTO) throws Exception{
        Category existingCategory = categoryService.getCategoryById(createProductDTO.getCategoryId());
        Author existingAuthor = authorService.getAuthorById(createProductDTO.getAuthorId());

        modelMapper.typeMap(CreateProductDTO.class, Product.class);

        Product newProduct = new Product();
        modelMapper.map(createProductDTO, newProduct);

        newProduct.setCategoryNode(existingCategory);
        newProduct.setAuthorNode(existingAuthor);

        return productRepository.save(newProduct);
    }
}
