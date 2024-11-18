package com.kadir.service.impl;

import com.kadir.dto.DtoCategory;
import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;
import com.kadir.exception.BaseException;
import com.kadir.exception.ErrorMessage;
import com.kadir.exception.MessageType;
import com.kadir.model.Category;
import com.kadir.model.Product;
import com.kadir.repository.CategoryRepository;
import com.kadir.repository.ProductRepository;
import com.kadir.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, DtoProductIU, DtoProduct> implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    protected JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

    @Override
    protected Product mapDtoToEntity(DtoProductIU dto, Product existingEntity) {
        Optional<Category> category = categoryRepository.findById(dto.getCategoryId());
        if (category.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found"));
        }
        if (existingEntity == null) {
            existingEntity = new Product();
            existingEntity.setCreatedAt(LocalDateTime.now());
        }
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity.setName(dto.getName());
        existingEntity.setDescription(dto.getDescription());
        existingEntity.setPrice(dto.getPrice());
        existingEntity.setStockQuantity(dto.getStockQuantity());
        existingEntity.setCategory(category.get());
        return existingEntity;
    }

    @Override
    protected DtoProduct mapEntityToDto(Product entity) {
        DtoProduct dto = new DtoProduct();
        BeanUtils.copyProperties(entity, dto);
        dto.setCreatedDate(entity.getCreatedAt());
        dto.setUpdatedDate(entity.getUpdatedAt());
        return dto;
    }


    @Override
    public DtoProduct createProduct(DtoProductIU dtoProductIU) {
        Product savedProduct = productRepository.save(mapDtoToEntity(dtoProductIU, null));
        DtoProduct dtoProduct = new DtoProduct();
        DtoCategory category = new DtoCategory();
        BeanUtils.copyProperties(savedProduct, dtoProduct);
        BeanUtils.copyProperties(savedProduct.getCategory(), category);

        dtoProduct.setName(savedProduct.getName());
        dtoProduct.setDescription(savedProduct.getDescription());
        dtoProduct.setPrice(savedProduct.getPrice());
        dtoProduct.setStockQuantity(savedProduct.getStockQuantity());
        dtoProduct.setCreatedDate(savedProduct.getCreatedAt());
        dtoProduct.setUpdatedDate(savedProduct.getUpdatedAt());
        return dtoProduct;
    }

    @Override
    public DtoProduct updateProduct(Long id, DtoProductIU dtoProductIU) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found"));
        }

        Product existingProduct = optionalProduct.get();
        Product updatedProduct = mapDtoToEntity(dtoProductIU, existingProduct);
        Product savedProduct = productRepository.save(updatedProduct);

        DtoProduct updatedDtoProduct = new DtoProduct();
        DtoCategory category = new DtoCategory();
        BeanUtils.copyProperties(savedProduct, updatedDtoProduct);
        BeanUtils.copyProperties(savedProduct.getCategory(), category);
        return updatedDtoProduct;
    }

    @Override
    public DtoProduct deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found"));
        }
        DtoProduct dtoProduct = new DtoProduct();

        productRepository.delete(optionalProduct.get());
        BeanUtils.copyProperties(optionalProduct.get(), dtoProduct);
        dtoProduct.setCreatedDate(optionalProduct.get().getCreatedAt());
        dtoProduct.setUpdatedDate(optionalProduct.get().getUpdatedAt());

        return dtoProduct;
    }

    @Override
    public List<DtoProduct> getAllProducts() {
        return productRepository.findAll().stream()
                .map(category -> {
                    DtoProduct dto = new DtoProduct();
                    BeanUtils.copyProperties(category, dto);
                    dto.setCreatedDate(category.getCreatedAt());
                    dto.setUpdatedDate(category.getUpdatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public DtoProduct getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found"));
        }
        Product product = optionalProduct.get();
        DtoProduct dto = new DtoProduct();
        BeanUtils.copyProperties(product, dto);
        dto.setCreatedDate(product.getCreatedAt());
        dto.setUpdatedDate(product.getUpdatedAt());
        return dto;
    }

}
