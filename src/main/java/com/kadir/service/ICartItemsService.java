package com.kadir.service;

import com.kadir.dto.DtoCartItems;
import com.kadir.dto.DtoCartItemsIU;

import java.util.List;

public interface ICartItemsService {

    DtoCartItems createCartItems(DtoCartItemsIU dtoCartItemsIU);

    DtoCartItems updateCartItems(Long id, DtoCartItemsIU dtoCartItemsIU);

    DtoCartItems deleteCartItems(Long id);

    DtoCartItems getCartItemsById(Long id);

    List<DtoCartItems> getUserCartItems(Long userId);

}
