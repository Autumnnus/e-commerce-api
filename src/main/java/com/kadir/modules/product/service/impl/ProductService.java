package com.kadir.modules.product.service.impl;

import com.kadir.common.dto.openai.OpenAiResponseDto;
import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.utils.merge.MergeUtils;
import com.kadir.common.utils.pagination.PageableHelper;
import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.category.model.Category;
import com.kadir.modules.category.repository.CategoryRepository;
import com.kadir.modules.product.dto.ProductAIRequestDto;
import com.kadir.modules.product.dto.ProductCreateDto;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.product.dto.ProductUpdateDto;
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import com.kadir.modules.product.service.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Value("${openai.api.key}")
    private String OPENAI_API_KEY;

    @Override
    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        Category category = categoryRepository.findById(productCreateDto.getCategoryId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));
        Product product = modelMapper.map(productCreateDto, Product.class);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        if (productUpdateDto.getCategoryId() != null) {
            Category existingCategory = categoryRepository.findById(productUpdateDto.getCategoryId())
                    .orElseThrow(() -> new BaseException(new ErrorMessage(
                            MessageType.GENERAL_EXCEPTION, "Category not found")));
            existingProduct.setCategory(existingCategory);
        }
        MergeUtils.copyNonNullProperties(productUpdateDto, existingProduct);
        Product savedProduct = productRepository.save(existingProduct);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Transactional
    @Override
    public ProductDto deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productRepository.deleteById(id);
        return productDto;
    }

    @Override
    public RestPageableEntity<ProductDto> getAllProducts(RestPageableRequest request) {
        Pageable pageable = PageableHelper
                .createPageable(request.getPageNumber(), request.getPageSize(), request.getSortBy(),
                        request.isAsc());
        Page<Product> productPage = productRepository.findAll(pageable);
        RestPageableEntity<ProductDto> pageableResponse = PaginationUtils.toPageableResponse(productPage,
                ProductDto.class, modelMapper);
        pageableResponse.setDocs(productPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList()));
        return pageableResponse;
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));

        Category category = existingProduct.getCategory() != null
                ? categoryRepository.findById(existingProduct.getCategory().getId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(
                        MessageType.GENERAL_EXCEPTION, "Category not found")))
                : null;
        ProductDto productDto = modelMapper.map(existingProduct, ProductDto.class);
        productDto.setCategory(category);
        return productDto;
    }

    @Override
    public List<String> getProductRecommendationByAI(ProductAIRequestDto productAIRequestDto) {
        if (productAIRequestDto.getContent() == null || productAIRequestDto.getContent().isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Content is required."));
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.openai.com/v1/chat/completions";

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("messages", new Object[]{
                    Map.of("role", "user", "content", productAIRequestDto.getContent())
            });

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "Bearer " + OPENAI_API_KEY);
            httpHeaders.set("Content-Type", "application/json");

            var requestEntity = new org.springframework.http.HttpEntity<>(requestBody, httpHeaders);
            ResponseEntity<OpenAiResponseDto> responseEntity =
                    restTemplate.postForEntity(url, requestEntity, OpenAiResponseDto.class);

            OpenAiResponseDto responseBody = responseEntity.getBody();
            if (responseBody == null || responseBody.getChoices().isEmpty()) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "No response generated."));
            }

            return responseBody.getChoices().stream()
                    .map(choice -> choice.getMessage().getContent())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
        }
    }

}
