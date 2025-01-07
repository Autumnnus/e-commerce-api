package com.kadir.modules.productviews.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.productviews.dto.ProductViewsDto;
import com.kadir.modules.productviews.dto.ProductViewsSaveDto;

public interface IProductViewsController {

    ApiResponse<ProductViewsDto> saveProductView(ProductViewsSaveDto productViewsSaveDto);

    ApiResponse<ProductViewsDto> getProductView(Long productId);

}
