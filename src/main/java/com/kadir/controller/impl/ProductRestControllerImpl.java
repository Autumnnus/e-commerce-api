package com.kadir.controller.impl;

import com.kadir.controller.IProductRestController;
import com.kadir.controller.RootEntity;
import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoProductIU;
import com.kadir.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/product")
public class ProductRestControllerImpl extends RestBaseController implements IProductRestController {

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
    public RootEntity<List<DtoProduct>> getAllProducts() {
        return RootEntity.success(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoProduct> getProductById(@PathVariable(name = "id") Long id) {
        return RootEntity.success(productService.getProductById(id));
    }
}
