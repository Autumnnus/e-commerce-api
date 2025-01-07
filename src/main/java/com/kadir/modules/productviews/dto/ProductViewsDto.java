package com.kadir.modules.productviews.dto;

import com.kadir.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductViewsDto extends BaseDto {

    private Long productId;

    private LocalDateTime viewDate;

    private String ipAddress;

    private int viewCount;
}
