package com.kadir.modules.productimage.dto;

import com.kadir.common.dto.BaseDto;
import com.kadir.modules.product.dto.ProductDto;
import lombok.Data;

@Data
public class ProductImageDto extends BaseDto {

    private String imageUrl;

    private String altText;

    private boolean isPrimary;

    private ProductDto product;
}
