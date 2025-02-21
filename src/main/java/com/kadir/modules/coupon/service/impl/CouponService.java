package com.kadir.modules.coupon.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.common.utils.merge.MergeUtils;
import com.kadir.common.utils.pagination.PageableHelper;
import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import com.kadir.modules.coupon.dto.CouponCreateDto;
import com.kadir.modules.coupon.dto.CouponDto;
import com.kadir.modules.coupon.dto.CouponUpdateDto;
import com.kadir.modules.coupon.model.Coupon;
import com.kadir.modules.coupon.repository.CouponRepository;
import com.kadir.modules.coupon.service.ICouponService;
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService implements ICouponService {

    private final ModelMapper modelMapper;
    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public CouponDto createCoupon(CouponCreateDto couponCreateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));
        Coupon coupon = modelMapper.map(couponCreateDto, Coupon.class);
        if (couponCreateDto.getProductId() != null) {
            Product product = productRepository.findById(couponCreateDto.getProductId())
                    .orElseThrow(() -> new BaseException(
                            new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));
            coupon.setProduct(product);
        }

        coupon.setUser(user);
        Coupon savedCoupon = couponRepository.save(coupon);
        return modelMapper.map(savedCoupon, CouponDto.class);
    }

    @Override
    public CouponDto updateCoupon(Long couponId, CouponUpdateDto couponUpdateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));
        Coupon existingCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Coupon not found")));
        if (!existingCoupon.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(
                    MessageType.UNAUTHORIZED, "You are not authorized to update this coupon"));
        }
        MergeUtils.copyNonNullProperties(couponUpdateDto, existingCoupon);
        Coupon updatedCoupon = couponRepository.save(existingCoupon);
        return modelMapper.map(updatedCoupon, CouponDto.class);
    }

    @Override
    public CouponDto deleteCoupon(Long couponId) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Coupon existingCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Coupon not found")));
        if (!existingCoupon.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(
                    MessageType.UNAUTHORIZED, "You are not authorized to delete this coupon"));
        }
        CouponDto couponDto = modelMapper.map(existingCoupon, CouponDto.class);
        couponRepository.delete(existingCoupon);
        return couponDto;
    }

    @Override
    public CouponDto getCouponByCode(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Coupon not found")));
        return modelMapper.map(coupon, CouponDto.class);
    }

    @Override
    public Boolean isCouponValid(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Coupon not found")));
        return coupon.isActive();
    }

    @Override
    public RestPageableEntity<CouponDto> getAllUserCoupons(RestPageableRequest request) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Pageable pageable = PageableHelper
                .createPageable(request.getPageNumber(), request.getPageSize(), request.getSortBy(),
                        request.isAsc());
        Page<Coupon> coupons = couponRepository.findByUserId(userId, pageable);
        RestPageableEntity<CouponDto> pageableResponse = PaginationUtils.toPageableResponse(coupons,
                CouponDto.class, modelMapper);
        return pageableResponse;
    }
}
