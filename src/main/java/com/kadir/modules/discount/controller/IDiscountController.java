package com.kadir.modules.discount.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.discount.dto.DiscountCreateDto;
import com.kadir.modules.discount.dto.DiscountDto;

public interface IDiscountController {

    ApiResponse<DiscountDto> createDiscount(DiscountCreateDto discountCreateDto);

    ApiResponse<DiscountDto> deleteDiscount(Long discountId);

    ApiResponse<RestPageableEntity<DiscountDto>> getAllDiscounts(RestPageableRequest request);

    ApiResponse<DiscountDto> getDiscountByProductId(Long productId);

    ApiResponse<DiscountDto> getDiscountByCategoryId(Long categoryId);
}
