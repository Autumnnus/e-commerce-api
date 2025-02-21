package com.kadir.modules.wishlist.service.impl;

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
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import com.kadir.modules.wishlist.dto.WishlistDto;
import com.kadir.modules.wishlist.model.Wishlist;
import com.kadir.modules.wishlist.repository.WishlistRepository;
import com.kadir.modules.wishlist.service.IWishlistService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService implements IWishlistService {

    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public WishlistDto toggleProductToWishlist(Long productId) {
        Long userId = authenticationServiceImpl.getCurrentUserId();

        return wishlistRepository.findByUserIdAndProductId(userId, productId)
                .map(wishlist -> {
                    wishlist.setActive(!wishlist.isActive());
                    Wishlist updatedWishlist = wishlistRepository.save(wishlist);
                    return modelMapper.map(updatedWishlist, WishlistDto.class);
                })
                .orElseGet(() -> {
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new BaseException(
                                    new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new BaseException(
                                    new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));

                    Wishlist wishlist = new Wishlist();
                    wishlist.setUser(user);
                    wishlist.setProduct(product);
                    Wishlist savedWishlist = wishlistRepository.save(wishlist);
                    return modelMapper.map(savedWishlist, WishlistDto.class);
                });
    }

    @Override
    public RestPageableEntity<WishlistDto> getAllWishlist(RestPageableRequest request) {
        Pageable pageable = PageableHelper
                .createPageable(request.getPageNumber(), request.getPageSize(), request.getSortBy(),
                        request.isAsc());
        Page<Wishlist> wishlistPage = wishlistRepository.findAll(pageable);
        RestPageableEntity<WishlistDto> pageableResponse = PaginationUtils.toPageableResponse(wishlistPage,
                WishlistDto.class, modelMapper);
        return pageableResponse;
    }
}
