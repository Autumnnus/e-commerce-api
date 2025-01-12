package com.kadir.modules.discount.controller.impl;

import com.kadir.common.constants.Paths;
import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.discount.controller.IDiscountController;
import com.kadir.modules.discount.dto.DiscountCreateDto;
import com.kadir.modules.discount.dto.DiscountDto;
import com.kadir.modules.discount.service.IDiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Paths.BASE_PATH + "/discount")
public class DiscountController extends RestBaseController implements IDiscountController {

    @Autowired
    private IDiscountService discountService;

    @PostMapping
    @Override
    public ApiResponse<DiscountDto> createDiscount(@RequestBody @Valid DiscountCreateDto discountCreateDto) {
        return ok(discountService.createDiscount(discountCreateDto));
    }

    @DeleteMapping("/{discountId}")
    @Override
    public ApiResponse<DiscountDto> deleteDiscount(@PathVariable(name = "discountId") Long discountId) {
        return ok(discountService.deleteDiscount(discountId));
    }

    @GetMapping
    @Override
    public ApiResponse<RestPageableEntity<DiscountDto>> getAllDiscounts(RestPageableRequest request) {
        return ok(discountService.getAllDiscounts(request));
    }

    @GetMapping("/product/{productId}")
    @Override
    public ApiResponse<DiscountDto> getDiscountByProductId(@PathVariable(name = "productId") Long productId) {
        return ok(discountService.getDiscountByProductId(productId));
    }

    @GetMapping("/category/{categoryId}")
    @Override
    public ApiResponse<DiscountDto> getDiscountByCategoryId(@PathVariable(name = "categoryId") Long categoryId) {
        return ok(discountService.getDiscountByCategoryId(categoryId));
    }
}
