package com.kadir.modules.favoriteproduct.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.favoriteproduct.dto.FavoriteProductDto;

public interface IFavoriteProductController {

    ApiResponse<FavoriteProductDto> toggleProductToFavorite(Long productId);

    ApiResponse<RestPageableEntity<FavoriteProductDto>> getAllFavoriteProducts(RestPageableRequest request);
}
