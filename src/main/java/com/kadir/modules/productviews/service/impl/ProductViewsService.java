package com.kadir.modules.productviews.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import com.kadir.modules.productviews.dto.ProductViewsDto;
import com.kadir.modules.productviews.dto.ProductViewsSaveDto;
import com.kadir.modules.productviews.model.ProductViews;
import com.kadir.modules.productviews.repository.ProductViewsRepository;
import com.kadir.modules.productviews.service.IProductViewsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductViewsService implements IProductViewsService {

    private final ProductViewsRepository productViewsRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductViewsDto saveProductView(ProductViewsSaveDto productViewsSaveDto) {
        if (productViewsSaveDto.getUserId() == null && productViewsSaveDto.getIpAddress() == null) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User or IP address is required"));
        }

        User user = null;
        if (productViewsSaveDto.getUserId() != null) {

            user = userRepository.findById(productViewsSaveDto.getUserId()).orElseThrow(
                    () -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));
        }

        Product product = productRepository.findById(productViewsSaveDto.getProductId()).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));

        ProductViews existingView = productViewsRepository.findByProductIdAndCustomerIdOrIpAddress(
                productViewsSaveDto.getProductId(),
                productViewsSaveDto.getUserId(),
                productViewsSaveDto.getIpAddress()
        );

        if (existingView != null) {
            existingView.setViewCount(existingView.getViewCount() + 1);
            ProductViews savedProductView = productViewsRepository.save(existingView);
            return modelMapper.map(savedProductView, ProductViewsDto.class);

        }

        ProductViews productView = new ProductViews();
        productView.setProduct(product);
        productView.setUser(user);
        productView.setIpAddress(productViewsSaveDto.getIpAddress());
        productView.setViewDate(LocalDateTime.now());
        productView.setViewCount(1);
        ProductViews savedProductView = productViewsRepository.save(productView);

        return modelMapper.map(savedProductView, ProductViewsDto.class);
    }

    @Override
    public ProductViewsDto getProductView(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));

        Optional<ProductViews> productViews = productViewsRepository.findById(productId);
        if (productViews.isPresent()) {
            return modelMapper.map(productViews.get(), ProductViewsDto.class);
        }
        ProductViews productView = new ProductViews();
        productView.setProduct(product);
        productView.setViewCount(0);
        return modelMapper.map(productView, ProductViewsDto.class);
    }

}
