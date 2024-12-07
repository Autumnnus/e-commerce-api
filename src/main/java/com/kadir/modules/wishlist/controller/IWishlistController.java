package com.kadir.modules.wishlist.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.wishlist.dto.WishlistDto;

public interface IWishlistController {

    RootEntity<WishlistDto> toggleProductToWishlist(Long productId);

    RootEntity<RestPageableEntity<WishlistDto>> getAllWishlist(RestPageableRequest request);
}
