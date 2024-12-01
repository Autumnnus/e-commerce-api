package com.kadir.modules.cartitems.service;

import com.kadir.modules.cartitems.dto.CartItemsDto;
import com.kadir.modules.cartitems.dto.CartItemsDtoIU;

import java.util.List;

public interface ICartItemsService {

    CartItemsDto createCartItems(CartItemsDtoIU cartItemsDtoIU);

    CartItemsDto updateCartItems(Long id, CartItemsDtoIU cartItemsDtoIU);

    CartItemsDto deleteCartItems(Long id);

    CartItemsDto getCartItemsById(Long id);

    List<CartItemsDto> getUserCartItems(Long userId);

}
