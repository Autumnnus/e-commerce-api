package com.kadir.modules.favoriteproduct.controller.impl;

import com.kadir.common.constants.Paths;
import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.favoriteproduct.controller.IFavoriteProductController;
import com.kadir.modules.favoriteproduct.dto.FavoriteProductDto;
import com.kadir.modules.favoriteproduct.service.IFavoriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Paths.BASE_PATH + "/favoriteProduct")
public class FavoriteProductController extends RestBaseController implements IFavoriteProductController {

    @Autowired
    private IFavoriteProductService favoriteProductService;

    @PatchMapping("/{productId}")
    @Override
    public ApiResponse<FavoriteProductDto> toggleProductToFavorite(@PathVariable(name = "productId") Long productId) {
        return ok(favoriteProductService.toggleProductToFavorite(productId));
    }

    @GetMapping
    @Override
    public ApiResponse<RestPageableEntity<FavoriteProductDto>> getAllFavoriteProducts(RestPageableRequest request) {
        return ok(favoriteProductService.getAllFavoriteProducts(request));
    }
}
