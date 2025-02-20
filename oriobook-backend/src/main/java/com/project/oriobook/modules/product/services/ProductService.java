package com.project.oriobook.modules.product.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.oriobook.common.constants.ElasticIndexConst;
import com.project.oriobook.common.exceptions.ProductException;
import com.project.oriobook.common.utils.ElasticUtil;
import com.project.oriobook.common.utils.ValidationUtil;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.author.services.AuthorService;
import com.project.oriobook.modules.author.services.IAuthorService;
import com.project.oriobook.modules.category.entities.Category;
import com.project.oriobook.modules.category.services.CategoryService;
import com.project.oriobook.modules.category.services.ICategoryService;
import com.project.oriobook.modules.elastic.services.ElasticService;
import com.project.oriobook.modules.elastic.services.IElasticService;
import com.project.oriobook.modules.order_details.services.OrderDetailsService;
import com.project.oriobook.modules.product.dto.CreateProductDTO;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.repository.ProductRepository;
import com.project.oriobook.modules.product.responses.GetProductByIdResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ICategoryService categoryService;
    private final IAuthorService authorService;
    private final IElasticService elasticService;

    private final ElasticsearchClient elasticClient;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Override
    public SearchResponse<ObjectNode> getAllProducts(FindAllProductQueryDTO query) throws Exception {
        SearchRequest.Builder searchRequestBuilder = ElasticUtil.generateSearchRequestBuilder(
            query, ElasticIndexConst.PRODUCTS
        );

        BoolQuery.Builder boolQueryBuilder = ElasticUtil.generateBoolBaseQuery(query);

        // Add match query only if productName is not null
        if (!ValidationUtil.isNullOrBlankString(query.getProductName())) {
            boolQueryBuilder.must(m -> m
                .match(wc -> wc
                    .field("name")
                    .query(query.getProductName())
                    .fuzziness("AUTO")
                )
            );
        }

        // BoolQuery.Builder filterQueryBuilder = new BoolQuery.Builder();
        if (!ValidationUtil.isNullOrBlankString(query.getCategoryId())) {
            TermQuery termQuery = new TermQuery.Builder()
                .field("categoryNode.id.keyword")
                .value(query.getCategoryId())
                .build();

            boolQueryBuilder.filter(f -> f
                .term(termQuery));
        }

        if (!ValidationUtil.isNullOrBlankString(query.getAuthorId())) {
            TermQuery termQuery = new TermQuery.Builder()
                .field("authorNode.id.keyword")
                .value(query.getAuthorId())
                .build();

            boolQueryBuilder.filter(f -> f
                .term(termQuery));
        }

        // Add sort query only if sortByPrice is not null
        if (!ValidationUtil.isNullOrBlankString(query.getSortByPrice())) {
            searchRequestBuilder.sort(s -> s
                .field(f -> f
                    .field("price").order(ElasticUtil.getSortOrder(query.getSortByPrice()))
                )
            );
        }

        // Not do rating sort yet (apply for FindAllProductQueryDTO)
        if (!ValidationUtil.isNullOrBlankString(query.getSortByRating())) {
            searchRequestBuilder.sort(s -> s
                .field(f -> f
                    .field("rating").order(ElasticUtil.getSortOrder(query.getSortByRating()))
                )
            );
        }

        // Build query for the search request
        searchRequestBuilder
            .query(q -> q.bool(boolQueryBuilder.build()));

        SearchResponse<ObjectNode> searchResponse = ElasticUtil.searchRequest(searchRequestBuilder,
            elasticClient, ElasticIndexConst.PRODUCTS);

        return searchResponse;
    }

    @Override
    public Page<Product> getAllProductsToSync() {
        return productRepository.findAll(Pageable.unpaged());
    }

    @Override
    public Product getProductById(String id) throws Exception {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(ProductException.NotFound::new);

        return existingProduct;
    }

    @Override
    public GetProductByIdResponse getProductByIdFromElastic(String id) throws Exception {
        GetProductByIdResponse elasticProduct = elasticService.getDataFromElasticById(
            id, ElasticIndexConst.PRODUCTS, GetProductByIdResponse.class);

        return elasticProduct;
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