package com.kadir.modules.wishlist.controller.impl;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.wishlist.controller.IWishlistController;
import com.kadir.modules.wishlist.dto.WishlistDto;
import com.kadir.modules.wishlist.service.IWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/wishlist")
public class WishlistController extends RestBaseController implements IWishlistController {

    @Autowired
    private IWishlistService wishlistService;

    @PatchMapping("/{productId}")
    @Override
    public ApiResponse<WishlistDto> toggleProductToWishlist(@PathVariable(name = "productId") Long productId) {
        return ok(wishlistService.toggleProductToWishlist(productId));
    }

    @GetMapping
    @Override
    public ApiResponse<RestPageableEntity<WishlistDto>> getAllWishlist(RestPageableRequest request) {
        return ok(wishlistService.getAllWishlist(request));
    }
}
