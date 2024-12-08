package com.kadir.modules.coupon.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.coupon.controller.ICouponController;
import com.kadir.modules.coupon.dto.CouponCreateDto;
import com.kadir.modules.coupon.dto.CouponDto;
import com.kadir.modules.coupon.dto.CouponUpdateDto;
import com.kadir.modules.coupon.service.ICouponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/coupon")
public class CouponController extends RestBaseController implements ICouponController {

    @Autowired
    private ICouponService couponService;

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @PostMapping
    @Override
    public RootEntity<CouponDto> createCoupon(@RequestBody @Valid CouponCreateDto couponCreateDto) {
        return ok(couponService.createCoupon(couponCreateDto));
    }

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @PutMapping(path = "/{couponId}")
    @Override
    public RootEntity<CouponDto> updateCoupon(@PathVariable(name = "couponId") Long couponId, @RequestBody @Valid CouponUpdateDto couponUpdateDto) {
        return ok(couponService.updateCoupon(couponId, couponUpdateDto));
    }

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @DeleteMapping("/{couponId}")
    @Override
    public RootEntity<CouponDto> deleteCoupon(@PathVariable(name = "couponId") Long couponId) {
        return ok(couponService.deleteCoupon(couponId));
    }

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @GetMapping("/{code}")
    @Override
    public RootEntity<CouponDto> getCouponByCode(@PathVariable(name = "code") String code) {
        return ok(couponService.getCouponByCode(code));
    }

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @GetMapping("/valid/{code}")
    @Override
    public RootEntity<Boolean> isCouponValid(@PathVariable(name = "code") String code) {
        return ok(couponService.isCouponValid(code));
    }

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @GetMapping
    @Override
    public RootEntity<List<CouponDto>> getAllUserCoupons() {
        return ok(couponService.getAllUserCoupons());
    }
}
