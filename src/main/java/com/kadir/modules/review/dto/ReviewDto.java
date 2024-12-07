package com.kadir.modules.review.dto;

import com.kadir.common.enums.ReviewRate;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.product.model.Product;
import lombok.Data;

@Data
public class ReviewDto {

    private User user;

    private Product product;

    private ReviewRate rating;
}
