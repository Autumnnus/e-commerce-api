package com.kadir.modules.review.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.review.dto.ReviewCreateDto;
import com.kadir.modules.review.dto.ReviewDto;
import com.kadir.modules.review.dto.ReviewGetDto;
import com.kadir.modules.review.dto.ReviewUpdateDto;

public interface IReviewController {

    ApiResponse<ReviewDto> createReview(ReviewCreateDto reviewCreateDto);

    ApiResponse<ReviewDto> getReviewById(Long reviewId, ReviewGetDto reviewGetDto);

    ApiResponse<ReviewDto> updateReview(Long reviewId, ReviewUpdateDto reviewUpdateDto);

    ApiResponse<ReviewDto> deleteReview(Long reviewId);

    ApiResponse<RestPageableEntity<ReviewDto>> getReviewByProductId(Long productId, RestPageableRequest request);

    ApiResponse<Double> getAverageRatingByProductId(Long productId);
}
