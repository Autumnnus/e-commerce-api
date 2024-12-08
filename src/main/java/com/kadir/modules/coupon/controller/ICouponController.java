package com.kadir.modules.coupon.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.coupon.dto.CouponCreateDto;
import com.kadir.modules.coupon.dto.CouponDto;
import com.kadir.modules.coupon.dto.CouponUpdateDto;

import java.util.List;

public interface ICouponController {

    RootEntity<CouponDto> createCoupon(CouponCreateDto couponCreateDto);

    RootEntity<CouponDto> updateCoupon(Long couponId, CouponUpdateDto couponUpdateDto);

    RootEntity<CouponDto> deleteCoupon(Long couponId);

    RootEntity<CouponDto> getCouponByCode(String code);

    RootEntity<Boolean> isCouponValid(String code);

    RootEntity<List<CouponDto>> getAllUserCoupons();
}
