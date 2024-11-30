package com.kadir.modules.cartitems.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import com.kadir.modules.cartitems.dto.DtoCartItems;
import com.kadir.modules.cartitems.dto.DtoCartItemsIU;
import com.kadir.modules.cartitems.model.CartItems;
import com.kadir.modules.cartitems.repository.CartItemsRepository;
import com.kadir.modules.cartitems.service.ICartItemsService;
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemsService implements ICartItemsService {

    private final CartItemsRepository cartItemsRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public DtoCartItems createCartItems(DtoCartItemsIU dtoCartItemsIU) {
        User user = userRepository.findById(dtoCartItemsIU.getUserId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found")));
        Product product = productRepository.findById(dtoCartItemsIU.getProductId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        CartItems cartItems = new CartItems();
        cartItems.setUser(user);
        cartItems.setProduct(product);
        cartItems.setQuantity(dtoCartItemsIU.getQuantity());

        CartItems savedCartItems = cartItemsRepository.save(cartItems);
        return modelMapper.map(savedCartItems, DtoCartItems.class);
    }


    @Override
    public DtoCartItems updateCartItems(Long id, DtoCartItemsIU dtoCartItemsIU) {
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found")));
        cartItems.getUser().setId(dtoCartItemsIU.getUserId());
        cartItems.getProduct().setId(dtoCartItemsIU.getProductId());
        cartItems.setQuantity(dtoCartItemsIU.getQuantity());

        CartItems savedCartItems = cartItemsRepository.save(cartItems);
        return modelMapper.map(savedCartItems, DtoCartItems.class);
    }

    @Override
    public DtoCartItems deleteCartItems(Long id) {
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found")));
        DtoCartItems dtoCartItems = modelMapper.map(cartItems, DtoCartItems.class);

        cartItemsRepository.deleteById(id);
        return dtoCartItems;
    }


    @Override
    public DtoCartItems getCartItemsById(Long id) {
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found")));
        return modelMapper.map(cartItems, DtoCartItems.class);
    }

    @Override
    public List<DtoCartItems> getUserCartItems(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found")));
        List<CartItems> cartItems = cartItemsRepository.findByUserId(userId);
        return modelMapper.map(cartItems, new TypeToken<List<DtoCartItems>>() {
        }.getType());
    }
}
