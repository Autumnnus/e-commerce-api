package com.kadir.modules.wishlist.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.wishlist.dto.WishlistDto;

public interface IWishlistController {

    ApiResponse<WishlistDto> toggleProductToWishlist(Long productId);

    ApiResponse<RestPageableEntity<WishlistDto>> getAllWishlist(RestPageableRequest request);
}
