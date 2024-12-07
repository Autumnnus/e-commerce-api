package com.kadir.modules.review.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.review.dto.ReviewCreateDto;
import com.kadir.modules.review.dto.ReviewDto;
import com.kadir.modules.review.dto.ReviewGetDto;
import com.kadir.modules.review.dto.ReviewUpdateDto;

public interface IReviewController {

    RootEntity<ReviewDto> createReview(ReviewCreateDto reviewCreateDto);

    RootEntity<ReviewDto> getReviewById(Long reviewId, ReviewGetDto reviewGetDto);

    RootEntity<ReviewDto> updateReview(Long productId, ReviewUpdateDto reviewUpdateDto);

    RootEntity<ReviewDto> deleteReview(Long reviewId);

    RootEntity<RestPageableEntity<ReviewDto>> getReviewByProductId(Long productId, RestPageableRequest request);

    RootEntity<Double> getAverageRatingByProductId(Long productId);
}
