package com.kadir.modules.discount.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.discount.dto.DiscountCreateDto;
import com.kadir.modules.discount.dto.DiscountDto;

public interface IDiscountService {

    DiscountDto createDiscount(DiscountCreateDto discountCreateDto);

    DiscountDto deleteDiscount(Long discountId);

    RestPageableEntity<DiscountDto> getAllDiscounts(RestPageableRequest request);

    DiscountDto getDiscountByProductId(Long productId);

    DiscountDto getDiscountByCategoryId(Long categoryId);
}
