package com.kadir.modules.productimage.controller.impl;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.productimage.controller.IProductImageController;
import com.kadir.modules.productimage.dto.ProductImageCreateDto;
import com.kadir.modules.productimage.dto.ProductImageDto;
import com.kadir.modules.productimage.dto.ProductImageUpdateDto;
import com.kadir.modules.productimage.service.IProductImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/rest/api/productImage")
public class ProductImageController extends RestBaseController implements IProductImageController {

    @Autowired
    private IProductImageService productImageService;

    @PostMapping(consumes = "multipart/form-data")
    @Override
    public ApiResponse<List<ProductImageDto>> createProductImages(
            @RequestParam(value = "file") List<MultipartFile> files,
            @ModelAttribute @Valid ProductImageCreateDto productImageCreateDto) {
        return ok(productImageService.createProductImages(files, productImageCreateDto));
    }

    @PutMapping(consumes = "multipart/form-data")
    @Override
    public ApiResponse<ProductImageDto> updateProductImage(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @ModelAttribute @Valid ProductImageUpdateDto productImageUpdateDto) {
        return ok(productImageService.updateProductImage(file, productImageUpdateDto));
    }

    @DeleteMapping("/{productImageId}")
    @Override
    public ApiResponse<ProductImageDto> deleteProductImage(@PathVariable(name = "productImageId") Long productImageId) {
        return ok(productImageService.deleteProductImage(productImageId));
    }

    @GetMapping("/{productId}")
    @Override
    public ApiResponse<List<ProductImageDto>> getProductImages(@PathVariable(name = "productId") Long productId) {
        return ok(productImageService.getProductImages(productId));
    }
}
