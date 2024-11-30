package com.kadir.modules.cartitems.service;

import com.kadir.modules.cartitems.dto.DtoCartItems;
import com.kadir.modules.cartitems.dto.DtoCartItemsIU;

import java.util.List;

public interface ICartItemsService {

    DtoCartItems createCartItems(DtoCartItemsIU dtoCartItemsIU);

    DtoCartItems updateCartItems(Long id, DtoCartItemsIU dtoCartItemsIU);

    DtoCartItems deleteCartItems(Long id);

    DtoCartItems getCartItemsById(Long id);

    List<DtoCartItems> getUserCartItems(Long userId);

}
