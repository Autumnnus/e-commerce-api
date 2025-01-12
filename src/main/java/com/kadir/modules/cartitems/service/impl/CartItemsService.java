package com.kadir.modules.cartitems.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.common.utils.merge.MergeUtils;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import com.kadir.modules.cartitems.dto.CartItemsCreateDto;
import com.kadir.modules.cartitems.dto.CartItemsDto;
import com.kadir.modules.cartitems.dto.CartItemsUpdateDto;
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
    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Override
    public CartItemsDto createCartItems(CartItemsCreateDto cartItemsCreateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));
        Product product = productRepository.findById(cartItemsCreateDto.getProductId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));
        CartItems cartItems = new CartItems();
        cartItems.setUser(user);
        cartItems.setProduct(product);
        cartItems.setQuantity(cartItemsCreateDto.getQuantity());

        CartItems savedCartItems = cartItemsRepository.save(cartItems);
        return modelMapper.map(savedCartItems, CartItemsDto.class);
    }

    @Override
    public CartItemsDto updateCartItems(Long id, CartItemsUpdateDto cartItemsUpdateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "CartItems not found")));
        if (!cartItems.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(
                    MessageType.UNAUTHORIZED, "You are not authorized to update this cart items"));
        }
        MergeUtils.copyNonNullProperties(cartItemsUpdateDto, cartItems);
        CartItems savedCartItems = cartItemsRepository.save(cartItems);
        return modelMapper.map(savedCartItems, CartItemsDto.class);
    }

    @Override
    public CartItemsDto deleteCartItems(Long id) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "CartItems not found")));
        if (!cartItems.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(
                    MessageType.UNAUTHORIZED, "You are not authorized to delete this cart items"));
        }
        CartItemsDto cartItemsDto = modelMapper.map(cartItems, CartItemsDto.class);

        cartItemsRepository.deleteById(id);
        return cartItemsDto;
    }


    @Override
    public CartItemsDto getCartItemsById(Long id) {
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "CartItems not found")));
        return modelMapper.map(cartItems, CartItemsDto.class);
    }

    @Override
    public List<CartItemsDto> getUserCartItems(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));
        List<CartItems> cartItems = cartItemsRepository.findByUserId(userId);
        return modelMapper.map(cartItems, new TypeToken<List<CartItemsDto>>() {
        }.getType());
    }
}
