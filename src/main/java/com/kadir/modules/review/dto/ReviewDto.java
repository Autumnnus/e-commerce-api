package com.kadir.modules.review.dto;

import com.kadir.modules.authentication.dto.UserDto;
import com.kadir.modules.product.dto.ProductDto;
import lombok.Data;

@Data
public class ReviewDto {

    private UserDto user;

    private ProductDto product;

    private Integer rating;

    private String comment;
}
