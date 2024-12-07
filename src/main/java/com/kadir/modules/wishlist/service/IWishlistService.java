package com.kadir.modules.wishlist.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.wishlist.dto.WishlistDto;

public interface IWishlistService {

    WishlistDto toggleProductToWishlist(Long productId);

    RestPageableEntity<WishlistDto> getAllWishlist(RestPageableRequest request);
}
