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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product mapDtoToProduct(DtoProductIU dtoProductIU, Product existingProduct) {
        Optional<Category> category = categoryRepository.findById(dtoProductIU.getCategoryId());
        if (category.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Category not found"));
        }

        if (existingProduct != null) {
            existingProduct.setName(dtoProductIU.getName());
            existingProduct.setDescription(dtoProductIU.getDescription());
            existingProduct.setPrice(dtoProductIU.getPrice());
            existingProduct.setStockQuantity(dtoProductIU.getStockQuantity());
            existingProduct.setCategory(category.get());
            existingProduct.setUpdatedAt(LocalDateTime.now());
            return existingProduct;
        } else {
            Product newProduct = new Product();
            newProduct.setCreatedAt(LocalDateTime.now());
            newProduct.setUpdatedAt(LocalDateTime.now());
            newProduct.setName(dtoProductIU.getName());
            newProduct.setDescription(dtoProductIU.getDescription());
            newProduct.setPrice(dtoProductIU.getPrice());
            newProduct.setStockQuantity(dtoProductIU.getStockQuantity());
            newProduct.setCategory(category.get());
            return newProduct;
        }
    }


    @Override
    public DtoProduct createProduct(DtoProductIU dtoProductIU) {
        Product savedProduct = productRepository.save(mapDtoToProduct(dtoProductIU, null));
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
        Product updatedProduct = mapDtoToProduct(dtoProductIU, existingProduct);
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

        Product deletedProduct = optionalProduct.get();
        productRepository.delete(deletedProduct);

        BeanUtils.copyProperties(deletedProduct, dtoProduct);
        return dtoProduct;
    }

}
