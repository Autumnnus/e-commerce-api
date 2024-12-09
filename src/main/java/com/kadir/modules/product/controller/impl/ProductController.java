package com.kadir.modules.product.controller.impl;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.product.controller.IProductController;
import com.kadir.modules.product.dto.ProductCreateDto;
import com.kadir.modules.product.dto.ProductDto;
import com.kadir.modules.product.dto.ProductUpdateDto;
import com.kadir.modules.product.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/product")
public class ProductController extends RestBaseController implements IProductController {

    @Autowired
    private IProductService productService;

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @PostMapping
    @Override
    public ApiResponse<ProductDto> createProduct(@RequestBody @Valid ProductCreateDto productCreateDto) {
        return ApiResponse.success(productService.createProduct(productCreateDto));
    }

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Override
    public ApiResponse<ProductDto> updateProduct(@PathVariable(name = "id") Long id, @RequestBody @Valid ProductUpdateDto productUpdateDto) {
        return ApiResponse.success(productService.updateProduct(id, productUpdateDto));
    }

    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Override
    public ApiResponse<ProductDto> deleteProduct(@PathVariable(name = "id") Long id) {
        return ApiResponse.success(productService.deleteProduct(id));
    }


    @GetMapping
    @Override
    public ApiResponse<RestPageableEntity<ProductDto>> getAllProducts(RestPageableRequest request) {
        return ok(productService.getAllProducts(request));
    }

    @GetMapping("/{id}")
    @Override
    public ApiResponse<ProductDto> getProductById(@PathVariable(name = "id") Long id) {
        return ApiResponse.success(productService.getProductById(id));
    }
}
