package com.kadir.modules.cartitems.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.cartitems.dto.CartItemsDto;
import com.kadir.modules.cartitems.dto.CartItemsDtoIU;

import java.util.List;

public interface ICartItemsController {

    RootEntity<CartItemsDto> createCartItems(CartItemsDtoIU cartItemsDtoIU);

    RootEntity<CartItemsDto> updateCartItems(Long id, CartItemsDtoIU cartItemsDtoIU);

    RootEntity<CartItemsDto> deleteCartItems(Long id);

    RootEntity<CartItemsDto> getCartItemsById(Long id);

    RootEntity<List<CartItemsDto>> getUserCartItems(Long userId);
}
