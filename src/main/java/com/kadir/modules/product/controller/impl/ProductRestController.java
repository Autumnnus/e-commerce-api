package com.kadir.modules.product.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.product.controller.IProductRestController;
import com.kadir.modules.product.dto.DtoProduct;
import com.kadir.modules.product.dto.DtoProductIU;
import com.kadir.modules.product.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/product")
public class ProductRestController extends RestBaseController implements IProductRestController {

    @Autowired
    private IProductService productService;

    @PostMapping("/create")
    @Override
    public RootEntity<DtoProduct> createProduct(@RequestBody @Valid DtoProductIU dtoProductIU) {
        return RootEntity.success(productService.createProduct(dtoProductIU));
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoProduct> updateProduct(@PathVariable(name = "id") Long id, @RequestBody @Valid DtoProductIU dtoProductIU) {
        return RootEntity.success(productService.updateProduct(id, dtoProductIU));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<DtoProduct> deleteProduct(@PathVariable(name = "id") Long id) {
        return RootEntity.success(productService.deleteProduct(id));
    }

    @GetMapping("/all")
    @Override
    public RootEntity<RestPageableEntity<DtoProduct>> getAllProducts(RestPageableRequest request) {
        return ok(productService.getAllProducts(request.getPageNumber(), request.getPageSize()));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoProduct> getProductById(@PathVariable(name = "id") Long id) {
        return RootEntity.success(productService.getProductById(id));
    }
}
