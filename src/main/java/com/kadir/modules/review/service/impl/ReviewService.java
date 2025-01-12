package com.kadir.modules.review.service.impl;

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
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import com.kadir.modules.review.dto.ReviewCreateDto;
import com.kadir.modules.review.dto.ReviewDto;
import com.kadir.modules.review.dto.ReviewGetDto;
import com.kadir.modules.review.dto.ReviewUpdateDto;
import com.kadir.modules.review.model.Review;
import com.kadir.modules.review.repository.ReviewRepository;
import com.kadir.modules.review.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Override
    public ReviewDto createReview(ReviewCreateDto reviewCreateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Product product = productRepository.findById(reviewCreateDto.getProductId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));

        List<Review> existingReviews = reviewRepository.findByUserAndProduct(userId, reviewCreateDto.getProductId());
        if (!existingReviews.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Review already exists"));
        }


        Review review = modelMapper.map(reviewCreateDto, Review.class);
        review.setUser(user);
        review.setProduct(product);
        Review savedReview = reviewRepository.save(review);
        return modelMapper.map(savedReview, ReviewDto.class);
    }

    @Override
    public ReviewDto getReviewById(Long reviewId, ReviewGetDto reviewGetDto) {
        productRepository.findById(reviewGetDto.getProductId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Review not found")));
        return modelMapper.map(review, ReviewDto.class);
    }

    @Override
    public ReviewDto updateReview(Long reviewId, ReviewUpdateDto reviewUpdateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Review not found")));
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));

        if (!existingReview.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(
                    MessageType.UNAUTHORIZED, "You are not authorized to update this review"));
        }
        MergeUtils.copyNonNullProperties(reviewUpdateDto, existingReview);
        Review savedReview = reviewRepository.save(existingReview);
        return modelMapper.map(savedReview, ReviewDto.class);
    }

    @Override
    public ReviewDto deleteReview(Long reviewId) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Review not found")));
        if (!existingReview.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(
                    MessageType.UNAUTHORIZED, "You are not authorized to update this review"));
        }
        ReviewDto reviewDto = modelMapper.map(existingReview, ReviewDto.class);
        reviewRepository.deleteById(reviewId);
        return reviewDto;
    }

    @Override
    public RestPageableEntity<ReviewDto> getReviewByProductId(Long productId, RestPageableRequest request) {
        Pageable pageable = PageableHelper
                .createPageable(request.getPageNumber(), request.getPageSize(), request.getSortBy(),
                        request.isAsc());
        Page<Review> reviewPage = reviewRepository.findByProductId(productId, pageable);
        RestPageableEntity<ReviewDto> pageableResponse = PaginationUtils.toPageableResponse(reviewPage,
                ReviewDto.class, modelMapper);
        return pageableResponse;
    }

    @Override
    public Double getAverageRatingByProductId(Long productId) {
        return reviewRepository.getAverageRatingByProductId(productId);
    }
}
