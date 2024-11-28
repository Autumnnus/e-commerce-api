package com.kadir.service.impl;

import com.kadir.dto.DtoCartItems;
import com.kadir.dto.DtoCartItemsIU;
import com.kadir.exception.BaseException;
import com.kadir.exception.ErrorMessage;
import com.kadir.exception.MessageType;
import com.kadir.model.CartItems;
import com.kadir.model.Product;
import com.kadir.model.User;
import com.kadir.repository.CartItemsRepository;
import com.kadir.repository.ProductRepository;
import com.kadir.repository.UserRepository;
import com.kadir.service.ICartItemsService;
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
