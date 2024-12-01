package com.kadir.modules.product.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.category.model.Category;
import com.kadir.modules.category.repository.CategoryRepository;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.product.dto.ProductDtoIU;
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import com.kadir.modules.product.service.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDtoIU productDtoIU) {
        categoryRepository.findById(productDtoIU.getCategoryId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));
        Product product = modelMapper.map(productDtoIU, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDtoIU productDtoIU) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        Category existingCategory = categoryRepository.findById(productDtoIU.getCategoryId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));

        Product updatedProduct = modelMapper.map(productDtoIU, Product.class);

        updatedProduct.setId(existingProduct.getId());
        updatedProduct.setUpdatedAt(existingProduct.getUpdatedAt());
        updatedProduct.setCreatedAt(existingProduct.getCreatedAt());
        updatedProduct.setCategory(existingCategory);
        Product savedProduct = productRepository.save(updatedProduct);

        return modelMapper.map(savedProduct, ProductDto.class);
    }


    @Transactional
    @Override
    public ProductDto deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productRepository.deleteById(id);
        return productDto;
    }

    @Override
    public RestPageableEntity<ProductDto> getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Product> productPage = productRepository.findAll(pageable);
        RestPageableEntity<ProductDto> pageableResponse = PaginationUtils.toPageableResponse(productPage, ProductDto.class, modelMapper);
        pageableResponse.setDocs(productPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList()));
        return pageableResponse;
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        Category category = product.getCategory() != null ? categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found"))) : null;
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setCategory(category);
        return productDto;
    }

}
