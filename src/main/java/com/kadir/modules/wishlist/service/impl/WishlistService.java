package com.kadir.modules.wishlist.service.impl;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.wishlist.dto.WishlistCreateDto;
import com.kadir.modules.wishlist.dto.WishlistDto;
import com.kadir.modules.wishlist.service.IWishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService implements IWishlistService {

//    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Override
    public WishlistDto addProductToWishlist(WishlistCreateDto wishlistCreateDto) {
//        Long userId = authenticationServiceImpl.getCurrentUserId();

        return null;
    }

    @Override
    public WishlistDto removeProductFromWishlist(int productId) {
        return null;
    }

    @Override
    public RestPageableEntity<WishlistDto> getAllWishlist(RestPageableRequest request) {
        return null;
    }
}
