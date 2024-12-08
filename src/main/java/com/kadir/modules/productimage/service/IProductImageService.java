package com.kadir.modules.productimage.service;

import com.kadir.modules.productimage.dto.ProductImageCreateDto;
import com.kadir.modules.productimage.dto.ProductImageDto;
import com.kadir.modules.productimage.dto.ProductImageUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductImageService {

    ProductImageDto createProductImage(MultipartFile file, ProductImageCreateDto productImageCreateDto);

    ProductImageDto updateProductImage(MultipartFile file, ProductImageUpdateDto productImageUpdateDto);

    ProductImageDto deleteProductImage(Long productImageId);

    List<ProductImageDto> getProductImages(Long productId);
}
