package com.kadir.modules.product.dto;

import com.kadir.common.dto.BaseDto;
import com.kadir.modules.authentication.dto.SellerDto;
import com.kadir.modules.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends BaseDto {

    private String name;

    private String description;

    private BigDecimal price;

    private int stockQuantity;

    private Category category;

    private SellerDto seller;
}
