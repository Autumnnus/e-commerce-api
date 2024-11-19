package com.kadir.controller.impl;

import com.kadir.controller.ICartItemsRestController;
import com.kadir.controller.RootEntity;
import com.kadir.dto.DtoCartItems;
import com.kadir.dto.DtoCartItemsIU;
import com.kadir.service.ICartItemsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/cart-items")
public class CartItemsRestController extends RestBaseController implements ICartItemsRestController {

    @Autowired
    private ICartItemsService cartItemsService;

    @PostMapping("/create")
    @Override
    public RootEntity<DtoCartItems> createCartItems(@RequestBody @Valid DtoCartItemsIU dtoCartItemsIU) {
        return ok(cartItemsService.createCartItems(dtoCartItemsIU));
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoCartItems> updateCartItems(@PathVariable(name = "id") Long id, @RequestBody @Valid DtoCartItemsIU dtoCartItemsIU) {
        return ok(cartItemsService.updateCartItems(id, dtoCartItemsIU));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<DtoCartItems> deleteCartItems(@PathVariable(name = "id") Long id) {
        return ok(cartItemsService.deleteCartItems(id));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoCartItems> getCartItemsById(@PathVariable(name = "id") Long id) {
        return ok(cartItemsService.getCartItemsById(id));
    }
}
