package com.kadir.modules.review.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.review.dto.ReviewCreateDto;
import com.kadir.modules.review.dto.ReviewDto;
import com.kadir.modules.review.dto.ReviewGetDto;
import com.kadir.modules.review.dto.ReviewUpdateDto;

public interface IReviewService {

    ReviewDto createReview(ReviewCreateDto reviewCreateDto);

    ReviewDto getReviewById(Long reviewId, ReviewGetDto reviewGetDto);

    ReviewDto updateReview(Long reviewId, ReviewUpdateDto reviewUpdateDto);

    ReviewDto deleteReview(Long reviewId);

    RestPageableEntity<ReviewDto> getReviewByProductId(Long productId, RestPageableRequest request);

    Double getAverageRatingByProductId(Long productId);
}
