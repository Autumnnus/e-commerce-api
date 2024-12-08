package com.kadir.modules.discount.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.discount.dto.DiscountCreateDto;
import com.kadir.modules.discount.dto.DiscountDto;

public interface IDiscountController {

    RootEntity<DiscountDto> createDiscount(DiscountCreateDto discountCreateDto);

    RootEntity<DiscountDto> deleteDiscount(Long discountId);

    RootEntity<RestPageableEntity<DiscountDto>> getAllDiscounts(RestPageableRequest request);

    RootEntity<DiscountDto> getDiscountByProductId(Long productId);

    RootEntity<DiscountDto> getDiscountByCategoryId(Long categoryId);
}
