package com.kadir.modules.productviews.service;

import com.kadir.modules.productviews.dto.ProductViewsDto;
import com.kadir.modules.productviews.dto.ProductViewsSaveDto;

public interface IProductViewsService {

    ProductViewsDto saveProductView(ProductViewsSaveDto productViewsSaveDto);

    ProductViewsDto getProductView(Long productId);
}
