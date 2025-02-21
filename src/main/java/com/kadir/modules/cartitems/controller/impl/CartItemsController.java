package com.kadir.modules.cartitems.controller.impl;

import com.kadir.common.constants.Paths;
import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.cartitems.controller.ICartItemsController;
import com.kadir.modules.cartitems.dto.CartItemsCreateDto;
import com.kadir.modules.cartitems.dto.CartItemsDto;
import com.kadir.modules.cartitems.dto.CartItemsUpdateDto;
import com.kadir.modules.cartitems.service.ICartItemsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Paths.BASE_PATH + "/cart-items")
public class CartItemsController extends RestBaseController implements ICartItemsController {

    @Autowired
    private ICartItemsService cartItemsService;

    @PostMapping
    @Override
    public ApiResponse<CartItemsDto> createCartItems(@RequestBody @Valid CartItemsCreateDto cartItemsCreateDto) {
        return ok(cartItemsService.createCartItems(cartItemsCreateDto));
    }

    @PutMapping(path = "/{id}")
    @Override
    public ApiResponse<CartItemsDto> updateCartItems(@PathVariable(name = "id") Long id, @RequestBody @Valid CartItemsUpdateDto cartItemsUpdateDto) {
        return ok(cartItemsService.updateCartItems(id, cartItemsUpdateDto));
    }

    @DeleteMapping("/{id}")
    @Override
    public ApiResponse<CartItemsDto> deleteCartItems(@PathVariable(name = "id") Long id) {
        return ok(cartItemsService.deleteCartItems(id));
    }

    @GetMapping("/{id}")
    @Override
    public ApiResponse<CartItemsDto> getCartItemsById(@PathVariable(name = "id") Long id) {
        return ok(cartItemsService.getCartItemsById(id));
    }

    @GetMapping("/user/{id}")
    @Override
    public ApiResponse<List<CartItemsDto>> getUserCartItems(@PathVariable(name = "id") Long userId) {
        return ok(cartItemsService.getUserCartItems(userId));
    }


}
