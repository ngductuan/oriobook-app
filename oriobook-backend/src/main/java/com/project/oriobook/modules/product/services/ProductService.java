package com.project.oriobook.modules.product.services;

import com.project.oriobook.common.helpers.QueryHelper;
import com.project.oriobook.common.utils.ValidationUtil;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;

    @Override
    public PageResponse<Product> getAllProducts(FindAllProductQueryDTO query) {
        List<Sort.Order> orders = QueryHelper.parseSortBase(query);

        if(ValidationUtil.isNullOrBlank(query.getSortByRating())){
            orders.add(new Sort.Order(Sort.Direction.fromString(query.getSortByRating().toString()), "rating"));
        }

        if(ValidationUtil.isNullOrBlank(query.getSortByPrice())){
            orders.add(new Sort.Order(Sort.Direction.fromString(query.getSortByPrice().toString()), "price"));
        }

        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by(orders));
        Page<Product> products = productRepository.findAll(query, pageRequest);
        return new PageResponse<>(products);
    }
}
