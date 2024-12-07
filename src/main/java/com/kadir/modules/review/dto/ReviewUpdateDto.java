package com.kadir.modules.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ReviewUpdateDto {

    @Min(0)
    @Max(4)
    private int rating;

    private String comment;
}
