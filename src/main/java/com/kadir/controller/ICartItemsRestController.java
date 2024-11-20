package com.kadir.controller;

import com.kadir.dto.DtoCartItems;
import com.kadir.dto.DtoCartItemsIU;

import java.util.List;

public interface ICartItemsRestController {

    RootEntity<DtoCartItems> createCartItems(DtoCartItemsIU dtoCartItemsIU);

    RootEntity<DtoCartItems> updateCartItems(Long id, DtoCartItemsIU dtoCartItemsIU);

    RootEntity<DtoCartItems> deleteCartItems(Long id);

    RootEntity<DtoCartItems> getCartItemsById(Long id);

    RootEntity<List<DtoCartItems>> getUserCartItems(Long userId);
}
