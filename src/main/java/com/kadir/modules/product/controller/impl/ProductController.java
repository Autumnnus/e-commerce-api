package com.kadir.modules.product.controller.impl;

import com.kadir.common.controller.RootEntity;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/product")
public class ProductController extends RestBaseController implements IProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    @Override
    public RootEntity<ProductDto> createProduct(@RequestBody @Valid ProductCreateDto productCreateDto) {
        return RootEntity.success(productService.createProduct(productCreateDto));
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<ProductDto> updateProduct(@PathVariable(name = "id") Long id, @RequestBody @Valid ProductUpdateDto productUpdateDto) {
        return RootEntity.success(productService.updateProduct(id, productUpdateDto));
    }

    @DeleteMapping("/{id}")
    @Override
    public RootEntity<ProductDto> deleteProduct(@PathVariable(name = "id") Long id) {
        return RootEntity.success(productService.deleteProduct(id));
    }

    @GetMapping
    @Override
    public RootEntity<RestPageableEntity<ProductDto>> getAllProducts(RestPageableRequest request) {
        return ok(productService.getAllProducts(request));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<ProductDto> getProductById(@PathVariable(name = "id") Long id) {
        return RootEntity.success(productService.getProductById(id));
    }
}
