package com.kadir.modules.review.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.review.controller.IReviewController;
import com.kadir.modules.review.dto.ReviewCreateDto;
import com.kadir.modules.review.dto.ReviewDto;
import com.kadir.modules.review.dto.ReviewGetDto;
import com.kadir.modules.review.dto.ReviewUpdateDto;
import com.kadir.modules.review.service.IReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/review")
public class ReviewController extends RestBaseController implements IReviewController {

    @Autowired
    private IReviewService reviewService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    @Override
    public RootEntity<ReviewDto> createReview(@RequestBody @Valid ReviewCreateDto reviewCreateDto) {
        return ok(reviewService.createReview(reviewCreateDto));
    }

    @GetMapping("/{reviewId}")
    @Override
    public RootEntity<ReviewDto> getReviewById(@PathVariable(name = "reviewId") Long reviewId, @RequestBody @Valid ReviewGetDto reviewGetDto) {
        return ok(reviewService.getReviewById(reviewId, reviewGetDto));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{reviewId}")
    @Override
    public RootEntity<ReviewDto> updateReview(@PathVariable(name = "reviewId") Long reviewId, @RequestBody @Valid ReviewUpdateDto reviewUpdateDto) {
        return ok(reviewService.updateReview(reviewId, reviewUpdateDto));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{reviewId}")
    @Override
    public RootEntity<ReviewDto> deleteReview(@PathVariable(name = "reviewId") Long reviewId) {
        return ok(reviewService.deleteReview(reviewId));
    }

    @GetMapping("/product/{productId}")
    @Override
    public RootEntity<RestPageableEntity<ReviewDto>> getReviewByProductId(@PathVariable(name = "productId") Long productId, RestPageableRequest request) {
        return ok(reviewService.getReviewByProductId(productId, request));
    }

    @GetMapping("/product/{productId}/averageRating")
    @Override
    public RootEntity<Double> getAverageRatingByProductId(@PathVariable(name = "productId") Long productId) {
        return ok(reviewService.getAverageRatingByProductId(productId));
    }
}
