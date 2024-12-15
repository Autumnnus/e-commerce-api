package com.kadir.modules.product.service.impl;

import com.google.gson.Gson;
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
import com.kadir.modules.product.dto.*;
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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
    private final WebClient webClient;

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
        String url = "/chat/completions";
        String userRequestContent = productAIRequestDto.getContent();
        String promptCategory =
                "Aşağıdaki yazdığım ürün için göre yukarıda listelediğin kategorilerden hangisi en uygun ise o verinin sadece id'sini yaz.Örnek format: id: 150, name: 'Bilgisayar'. ";
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "system", "content", "Ne tür bir ürün arıyorsunuz?"),
                        Map.of("role", "system", "content", "Kategori Listemiz: " + fetchCategories()),
                        Map.of("role", "user", "content", promptCategory + userRequestContent)
                )
        );

        try {
            OpenAiResponseDto response = webClient.post()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + OPENAI_API_KEY)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(OpenAiResponseDto.class)
                    .block();

            if (response == null || response.getChoices().isEmpty()) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "No response generated."));
            }
            String categoryId = response.getChoices().get(0).getMessage().getContent()
                    .replace("id: ", "").split(",")[0].trim();

            Long categoryIdLong;
            try {
                categoryIdLong = Long.parseLong(categoryId);
            } catch (NumberFormatException e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Invalid category ID format."));
            }

            List<Product> products = productRepository.findByCategoryId(categoryIdLong);

            List<Map<String, String>> productData = products.stream()
                    .map(product -> {
                        Map<String, String> productDetails = new HashMap<>();
                        productDetails.put("name", product.getName());
                        productDetails.put("price", product.getPrice().toString());
                        return productDetails;
                    })
                    .collect(Collectors.toList());

            return List.of(new Gson().toJson(productData));

//            return response.getChoices().stream()
//                    .map(choice -> choice.getMessage().getContent())
//                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
        }
    }

    private String fetchProductByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        List<Map<String, String>> productData = products.stream()
                .map(product -> {
                    Map<String, String> productDetails = new HashMap<>();
                    productDetails.put("name", product.getName());
                    productDetails.put("price", product.getPrice().toString());
                    return productDetails;
                })
                .collect(Collectors.toList());

        return new Gson().toJson(productData);
    }

    private String fetchCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<Map<String, String>> categoryData = categories.stream()
                .map(category -> {
                    Map<String, String> categoryDetails = new HashMap<>();
                    categoryDetails.put("name", category.getName());
                    categoryDetails.put("description", category.getDescription());
                    return categoryDetails;
                })
                .collect(Collectors.toList());

        return new Gson().toJson(categoryData);
    }


    private ProductRecommendationDto mapProductToRecommendation(Product product, String aiGeneratedText) {
        ProductRecommendationDto recommendation = new ProductRecommendationDto();
        recommendation.setId(product.getId());
        recommendation.setName(product.getName());
        recommendation.setPrice(product.getPrice());
        recommendation.setReasonsToBuy("Uygun fiyat, kaliteli malzeme");
        recommendation.setReasonsToAvoid("Alternatif ürünler mevcut");
        recommendation.setComparison("Bu ürün benzerlerine göre %10 daha ucuz.");
        return recommendation;
    }

}
