package com.kadir.modules.productviews.controller.impl;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.productviews.controller.IProductViewsController;
import com.kadir.modules.productviews.dto.ProductViewsDto;
import com.kadir.modules.productviews.dto.ProductViewsSaveDto;
import com.kadir.modules.productviews.service.impl.ProductViewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/product-views")
public class ProductViewsControllerImpl extends RestBaseController implements IProductViewsController {

    @Autowired
    private ProductViewsService productViewsService;

    @PostMapping("/save")
    @Override
    public ApiResponse<ProductViewsDto> saveProductView(@RequestBody @Valid ProductViewsSaveDto productViewsSaveDto) {
        return ok(productViewsService.saveProductView(productViewsSaveDto));
    }

    @GetMapping("/{productId}")
    @Override
    public ApiResponse<ProductViewsDto> getProductView(@PathVariable(name = "productId") Long productId) {
        return ok(productViewsService.getProductView(productId));
    }
}
