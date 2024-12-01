package com.kadir.modules.cartitems.service;

import com.kadir.modules.cartitems.dto.CartItemsCreateDto;
import com.kadir.modules.cartitems.dto.CartItemsDto;
import com.kadir.modules.cartitems.dto.CartItemsUpdateDto;

import java.util.List;

public interface ICartItemsService {

    CartItemsDto createCartItems(CartItemsCreateDto cartItemsCreateDto);

    CartItemsDto updateCartItems(Long id, CartItemsUpdateDto cartItemsUpdateDto);

    CartItemsDto deleteCartItems(Long id);

    CartItemsDto getCartItemsById(Long id);

    List<CartItemsDto> getUserCartItems(Long userId);

}
