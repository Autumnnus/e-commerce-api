package com.kadir.modules.coupon.service;

import com.kadir.modules.coupon.dto.CouponCreateDto;
import com.kadir.modules.coupon.dto.CouponDto;
import com.kadir.modules.coupon.dto.CouponUpdateDto;

import java.util.List;

public interface ICouponService {

    CouponDto createCoupon(CouponCreateDto couponCreateDto);

    CouponDto updateCoupon(Long couponId, CouponUpdateDto couponUpdateDto);

    CouponDto deleteCoupon(Long couponId);

    CouponDto getCouponByCode(String code);

    Boolean isCouponValid(String code);

    List<CouponDto> getAllUserCoupons();
}
