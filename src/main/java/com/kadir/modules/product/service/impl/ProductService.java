package com.kadir.modules.product.service.impl;

import com.google.gson.Gson;
import com.kadir.common.dto.openai.OpenAiResponseDto;
import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.common.utils.merge.MergeUtils;
import com.kadir.common.utils.openai.OpenAiUtil;
import com.kadir.common.utils.pagination.PageableHelper;
import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.authentication.model.Seller;
import com.kadir.modules.authentication.repository.SellerRepository;
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
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final WebClient webClient;
    private final OpenAiUtil openAiUtil;
    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Override
    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Seller seller = sellerRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Seller not found")));
        Category category = categoryRepository.findById(productCreateDto.getCategoryId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Category not found")));
        Product product = modelMapper.map(productCreateDto, Product.class);
        product.setSeller(seller);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));
        if (!existingProduct.getSeller().getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(
                    MessageType.UNAUTHORIZED, "You are not authorized to update this product"));
        }

        if (productUpdateDto.getCategoryId() != null) {
            Category existingCategory = categoryRepository.findById(productUpdateDto.getCategoryId())
                    .orElseThrow(() -> new BaseException(new ErrorMessage(
                            MessageType.NO_RECORD_EXIST, "Category not found")));
            existingProduct.setCategory(existingCategory);
        }
        MergeUtils.copyNonNullProperties(productUpdateDto, existingProduct);
        Product savedProduct = productRepository.save(existingProduct);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Transactional
    @Override
    public ProductDto deleteProduct(Long id) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));

        if (!existingProduct.getSeller().getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(
                    MessageType.UNAUTHORIZED, "You are not authorized to delete this product"));
        }

        ProductDto productDto = modelMapper.map(existingProduct, ProductDto.class);
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
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));

        Category category = existingProduct.getCategory() != null
                ? categoryRepository.findById(existingProduct.getCategory().getId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(
                        MessageType.NO_RECORD_EXIST, "Category not found")))
                : null;
        ProductDto productDto = modelMapper.map(existingProduct, ProductDto.class);
        productDto.setCategory(category);
        return productDto;
    }

    private final ChatClient.Builder chatClientBuilder;
    private final EmbeddingModel embeddingModel;

    @Override
    public List<String> getProductRecommendationByAI(ProductAIRequestDto productAIRequestDto) {
        String url = "/chat/completions";
        String userRequestContent = productAIRequestDto.getContent();
//        ChatClient chatClient = chatClientBuilder.build();
//        String response = chatClient.prompt("Tell me a joke").call().content();
        EmbeddingResponse response = embeddingModel.call(
                new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"),
                        OpenAiEmbeddingOptions.builder()
                                .withModel("text-embedding-3-small")
                                .build()));
        System.out.println(response);
        if (response != null) {
            return null;
        }

        try {
            String categoryPrompt = "Aşağıdaki yazdığım ürün için göre yukarıda listelediğin kategorilerden hangisi en uygun ise şu formatta geriye yaz: Örnek format: id: 150, name: 'Bilgisayar'. " + userRequestContent;

            Map<String, Object> categoryRequestBody = openAiUtil.createRequestBody(List.of(
                    Map.of("role", "system", "content", "Ne tür bir ürün arıyorsunuz?"),
                    Map.of("role", "system", "content", "Kategori Listemiz: " + fetchCategories()),
                    Map.of("role", "user", "content", categoryPrompt)
            ));

            OpenAiResponseDto categoryResponse = openAiUtil.makeApiCall(url, categoryRequestBody, webClient);

            Long categoryIdLong = extractCategoryId(categoryResponse);
            String fetchedProducts = fetchProductByCategoryId(categoryIdLong);

            String productPrompt = "Şimdi ise yukarıda" + userRequestContent + "sorum için, sizin yukarıda paylaştığınız Ürünlerden hangi ürünü öneririsiniz? Neden almalıyım, neden almamalıyım, fiyat avantajları vs bunun bilgilerini ver. Her tavsiye edilecek üründen bahsederken id'sini de mutlaka bulundur.";

            Map<String, Object> productRequestBody = openAiUtil.createRequestBody(List.of(
                    Map.of("role", "system", "content", "Ürünlerimiz: " + fetchedProducts),
                    Map.of("role", "user", "content", productPrompt)
            ));

            OpenAiResponseDto productResponse = openAiUtil.makeApiCall(url, productRequestBody, webClient);

            return productResponse.getChoices().stream()
                    .map(choice -> choice.getMessage().getContent())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
        }
    }


    private Long extractCategoryId(OpenAiResponseDto response) {
        if (response == null || response.getChoices().isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "No response generated."));
        }
        String categoryId = response.getChoices().get(0).getMessage().getContent()
                .replace("id: ", "").split(",")[0].trim();
        try {
            return Long.parseLong(categoryId);
        } catch (NumberFormatException e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Invalid category ID format."));
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
                    categoryDetails.put("id", category.getId().toString());
                    categoryDetails.put("name", category.getName());
                    categoryDetails.put("description", category.getDescription());
                    return categoryDetails;
                })
                .collect(Collectors.toList());

        return new Gson().toJson(categoryData);
    }

}
