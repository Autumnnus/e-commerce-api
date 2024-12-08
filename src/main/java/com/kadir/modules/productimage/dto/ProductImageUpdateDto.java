package com.kadir.modules.productimage.dto;

import lombok.Data;

@Data
public class ProductImageUpdateDto {

    private Long id;

    private String altText;

    private boolean isPrimary;

}
