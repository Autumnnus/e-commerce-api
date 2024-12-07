package com.kadir.modules.review.dto;

import com.kadir.common.enums.ReviewRate;
import lombok.Data;

@Data
public class ReviewUpdateDto {

    private ReviewRate rating;

    private String comment;
}
