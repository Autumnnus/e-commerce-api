package com.kadir.modules.favoriteproduct.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.favoriteproduct.dto.FavoriteProductDto;

public interface IFavoriteProductService {

    FavoriteProductDto toggleProductToFavorite(Long productId);

    RestPageableEntity<FavoriteProductDto> getAllFavoriteProducts(RestPageableRequest request);
}
