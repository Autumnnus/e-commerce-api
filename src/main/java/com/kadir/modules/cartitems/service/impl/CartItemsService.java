package com.kadir.modules.cartitems.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import com.kadir.modules.cartitems.dto.CartItemsDto;
import com.kadir.modules.cartitems.dto.CartItemsDtoIU;
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
    public CartItemsDto createCartItems(CartItemsDtoIU cartItemsDtoIU) {
        User user = userRepository.findById(cartItemsDtoIU.getUserId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found")));
        Product product = productRepository.findById(cartItemsDtoIU.getProductId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        CartItems cartItems = new CartItems();
        cartItems.setUser(user);
        cartItems.setProduct(product);
        cartItems.setQuantity(cartItemsDtoIU.getQuantity());

        CartItems savedCartItems = cartItemsRepository.save(cartItems);
        return modelMapper.map(savedCartItems, CartItemsDto.class);
    }


    @Override
    public CartItemsDto updateCartItems(Long id, CartItemsDtoIU cartItemsDtoIU) {
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found")));
        cartItems.getUser().setId(cartItemsDtoIU.getUserId());
        cartItems.getProduct().setId(cartItemsDtoIU.getProductId());
        cartItems.setQuantity(cartItemsDtoIU.getQuantity());

        CartItems savedCartItems = cartItemsRepository.save(cartItems);
        return modelMapper.map(savedCartItems, CartItemsDto.class);
    }

    @Override
    public CartItemsDto deleteCartItems(Long id) {
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found")));
        CartItemsDto cartItemsDto = modelMapper.map(cartItems, CartItemsDto.class);

        cartItemsRepository.deleteById(id);
        return cartItemsDto;
    }


    @Override
    public CartItemsDto getCartItemsById(Long id) {
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found")));
        return modelMapper.map(cartItems, CartItemsDto.class);
    }

    @Override
    public List<CartItemsDto> getUserCartItems(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found")));
        List<CartItems> cartItems = cartItemsRepository.findByUserId(userId);
        return modelMapper.map(cartItems, new TypeToken<List<CartItemsDto>>() {
        }.getType());
    }
}
