package com.kadir.modules.cartitems.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.cartitems.dto.CartItemsCreateDto;
import com.kadir.modules.cartitems.dto.CartItemsDto;
import com.kadir.modules.cartitems.dto.CartItemsUpdateDto;

import java.util.List;

public interface ICartItemsController {

    ApiResponse<CartItemsDto> createCartItems(CartItemsCreateDto cartItemsCreateDto);

    ApiResponse<CartItemsDto> updateCartItems(Long id, CartItemsUpdateDto cartItemsUpdateDto);

    ApiResponse<CartItemsDto> deleteCartItems(Long id);

    ApiResponse<CartItemsDto> getCartItemsById(Long id);

    ApiResponse<List<CartItemsDto>> getUserCartItems(Long userId);
}
