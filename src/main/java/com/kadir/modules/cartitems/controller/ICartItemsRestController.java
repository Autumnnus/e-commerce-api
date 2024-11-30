package com.kadir.modules.cartitems.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.cartitems.dto.DtoCartItems;
import com.kadir.modules.cartitems.dto.DtoCartItemsIU;

import java.util.List;

public interface ICartItemsRestController {

    RootEntity<DtoCartItems> createCartItems(DtoCartItemsIU dtoCartItemsIU);

    RootEntity<DtoCartItems> updateCartItems(Long id, DtoCartItemsIU dtoCartItemsIU);

    RootEntity<DtoCartItems> deleteCartItems(Long id);

    RootEntity<DtoCartItems> getCartItemsById(Long id);

    RootEntity<List<DtoCartItems>> getUserCartItems(Long userId);
}
