package com.kadir.modules.coupon.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.coupon.dto.CouponCreateDto;
import com.kadir.modules.coupon.dto.CouponDto;
import com.kadir.modules.coupon.dto.CouponUpdateDto;

public interface ICouponController {

    ApiResponse<CouponDto> createCoupon(CouponCreateDto couponCreateDto);

    ApiResponse<CouponDto> updateCoupon(Long couponId, CouponUpdateDto couponUpdateDto);

    ApiResponse<CouponDto> deleteCoupon(Long couponId);

    ApiResponse<CouponDto> getCouponByCode(String code);

    ApiResponse<Boolean> isCouponValid(String code);

    ApiResponse<RestPageableEntity<CouponDto>> getAllUserCoupons(RestPageableRequest request);
}
