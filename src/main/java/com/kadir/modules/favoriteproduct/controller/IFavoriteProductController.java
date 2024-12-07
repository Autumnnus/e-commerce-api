package com.kadir.modules.favoriteproduct.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.favoriteproduct.dto.FavoriteProductDto;

public interface IFavoriteProductController {

    RootEntity<FavoriteProductDto> toggleProductToFavorite(Long productId);

    RootEntity<RestPageableEntity<FavoriteProductDto>> getAllFavoriteProducts(RestPageableRequest request);
}
