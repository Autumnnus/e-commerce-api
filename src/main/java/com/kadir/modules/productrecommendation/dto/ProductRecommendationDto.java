package com.kadir.modules.productrecommendation.dto;

import com.kadir.common.dto.BaseDto;
import com.kadir.common.enums.RecommendationType;
import com.kadir.modules.product.dto.ProductDto;
import lombok.Data;

@Data
public class ProductRecommendationDto extends BaseDto {

//    private ProductDto product;

    private ProductDto recommendedProduct;

    private RecommendationType recommendationType;
}
