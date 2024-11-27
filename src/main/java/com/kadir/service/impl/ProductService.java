package com.kadir.service.impl;

import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;
import com.kadir.exception.BaseException;
import com.kadir.exception.ErrorMessage;
import com.kadir.exception.MessageType;
import com.kadir.mapper.ProductMapper;
import com.kadir.model.Category;
import com.kadir.model.Product;
import com.kadir.repository.CategoryRepository;
import com.kadir.repository.ProductRepository;
import com.kadir.service.IProductService;
import com.kadir.utils.pagination.PaginationUtils;
import com.kadir.utils.pagination.RestPageableEntity;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DtoProduct createProduct(DtoProductIU dtoProductIU) {
        categoryRepository.findById(dtoProductIU.getCategoryId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));
        Product product = modelMapper.map(dtoProductIU, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, DtoProduct.class);
    }

    @Override
    public DtoProduct updateProduct(Long id, DtoProductIU dtoProductIU) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        Category existingCategory = categoryRepository.findById(dtoProductIU.getCategoryId()).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));
        Product updatedProduct = productMapper.mapDtoToEntity(dtoProductIU);

        updatedProduct.setId(existingProduct.getId());
        updatedProduct.setUpdatedAt(existingProduct.getUpdatedAt());
        updatedProduct.setCreatedAt(existingProduct.getCreatedAt());
        updatedProduct.setCategory(existingCategory);
        Product savedProduct = productRepository.save(updatedProduct);

        return productMapper.mapEntityToDto(savedProduct);
    }

    @Transactional
    @Override
    public DtoProduct deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        DtoProduct dtoProduct = productMapper.mapEntityToDto(product);
        productRepository.deleteById(id);
        return dtoProduct;
    }

    @Override
    public RestPageableEntity<DtoProduct> getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Product> productPage = productRepository.findAll(pageable);
        RestPageableEntity<DtoProduct> pageableResponse = PaginationUtils.toPageableResponse(productPage, DtoProduct.class, modelMapper);
        pageableResponse.setDocs(productMapper.mapEntityListToDtoList(productPage.getContent()));
        return pageableResponse;
    }


    @Override
    public DtoProduct getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        Category category = null;
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found")));
        }
        DtoProduct dtoProduct = productMapper.mapEntityToDto(product);
        dtoProduct.setCategory(category);
        return dtoProduct;
    }

}
