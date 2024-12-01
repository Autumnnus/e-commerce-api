package com.kadir.modules.cartitems.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.cartitems.dto.CartItemsCreateDto;
import com.kadir.modules.cartitems.dto.CartItemsDto;
import com.kadir.modules.cartitems.dto.CartItemsUpdateDto;

import java.util.List;

public interface ICartItemsController {

    RootEntity<CartItemsDto> createCartItems(CartItemsCreateDto cartItemsCreateDto);

    RootEntity<CartItemsDto> updateCartItems(Long id, CartItemsUpdateDto cartItemsUpdateDto);

    RootEntity<CartItemsDto> deleteCartItems(Long id);

    RootEntity<CartItemsDto> getCartItemsById(Long id);

    RootEntity<List<CartItemsDto>> getUserCartItems(Long userId);
}
