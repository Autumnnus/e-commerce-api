package com.kadir.modules.productimage.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.productimage.dto.ProductImageCreateDto;
import com.kadir.modules.productimage.dto.ProductImageDto;
import com.kadir.modules.productimage.dto.ProductImageUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductImageController {

    ApiResponse<ProductImageDto> createProductImage(MultipartFile file, ProductImageCreateDto productImageCreateDto);

    ApiResponse<ProductImageDto> updateProductImage(MultipartFile file, ProductImageUpdateDto productImageUpdateDto);

    ApiResponse<ProductImageDto> deleteProductImage(Long productImageId);

    ApiResponse<List<ProductImageDto>> getProductImages(Long productId);
}
