package com.kadir.modules.favoriteproduct.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.common.utils.pagination.PageableHelper;
import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import com.kadir.modules.favoriteproduct.dto.FavoriteProductDto;
import com.kadir.modules.favoriteproduct.model.FavoriteProduct;
import com.kadir.modules.favoriteproduct.repository.FavoriteProductRepository;
import com.kadir.modules.favoriteproduct.service.IFavoriteProductService;
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteProductService implements IFavoriteProductService {

    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final FavoriteProductRepository favoriteProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public FavoriteProductDto toggleProductToFavorite(Long productId) {
        Long userId = authenticationServiceImpl.getCurrentUserId();

        return favoriteProductRepository.findByUserIdAndProductId(userId, productId)
                .map(favoriteProduct -> {
                    favoriteProduct.setActive(!favoriteProduct.isActive());
                    FavoriteProduct updatedFavoriteProduct = favoriteProductRepository.save(favoriteProduct);
                    return modelMapper.map(updatedFavoriteProduct, FavoriteProductDto.class);
                })
                .orElseGet(() -> {
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new BaseException(
                                    new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new BaseException(
                                    new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));

                    FavoriteProduct favoriteProduct = new FavoriteProduct();
                    favoriteProduct.setUser(user);
                    favoriteProduct.setProduct(product);
                    FavoriteProduct savedFavoriteProduct = favoriteProductRepository.save(favoriteProduct);
                    return modelMapper.map(savedFavoriteProduct, FavoriteProductDto.class);
                });
    }

    @Override
    public RestPageableEntity<FavoriteProductDto> getAllFavoriteProducts(RestPageableRequest request) {
        Pageable pageable = PageableHelper
                .createPageable(request.getPageNumber(), request.getPageSize(), request.getSortBy(),
                        request.isAsc());
        Page<FavoriteProduct> favoriteProductPage = favoriteProductRepository.findAll(pageable);
        RestPageableEntity<FavoriteProductDto> pageableResponse = PaginationUtils.toPageableResponse(favoriteProductPage,
                FavoriteProductDto.class, modelMapper);
        return pageableResponse;
    }
}
