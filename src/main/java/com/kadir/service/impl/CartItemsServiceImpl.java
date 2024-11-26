package com.kadir.service.impl;

import com.kadir.dto.DtoCartItems;
import com.kadir.dto.DtoCartItemsIU;
import com.kadir.exception.BaseException;
import com.kadir.exception.ErrorMessage;
import com.kadir.exception.MessageType;
import com.kadir.mapper.CartItemsMapper;
import com.kadir.mapper.ProductMapper;
import com.kadir.mapper.UserMapper;
import com.kadir.model.CartItems;
import com.kadir.model.Product;
import com.kadir.model.User;
import com.kadir.repository.CartItemsRepository;
import com.kadir.repository.ProductRepository;
import com.kadir.repository.UserRepository;
import com.kadir.service.ICartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemsServiceImpl implements ICartItemsService {

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsMapper cartItemsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;
    
    @Override
    public DtoCartItems createCartItems(DtoCartItemsIU dtoCartItemsIU) {
        User user = userRepository.findById(dtoCartItemsIU.getUserId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found")));
        Product product = productRepository.findById(dtoCartItemsIU.getProductId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        CartItems cartItems = cartItemsMapper.mapDtoToEntity(dtoCartItemsIU);
        cartItems.getUser().setId(dtoCartItemsIU.getUserId());
        cartItems.getProduct().setId(dtoCartItemsIU.getProductId());

        CartItems savedCartItems = cartItemsRepository.save(cartItems);

        DtoCartItems dtoCartItems = cartItemsMapper.mapEntityToDto(savedCartItems);
        dtoCartItems.setUser(userMapper.mapEntityToDto(user));
        dtoCartItems.setProduct(productMapper.mapEntityToDto(product));

        return dtoCartItems;
    }


    @Override
    public DtoCartItems updateCartItems(Long id, DtoCartItemsIU dtoCartItemsIU) {
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found")));
        cartItems.getUser().setId(dtoCartItemsIU.getUserId());
        cartItems.getProduct().setId(dtoCartItemsIU.getProductId());
        cartItems.setQuantity(dtoCartItemsIU.getQuantity());

        CartItems savedCartItems = cartItemsRepository.save(cartItems);

        return cartItemsMapper.mapEntityToDto(savedCartItems);
    }

    @Override
    public DtoCartItems deleteCartItems(Long id) {
        CartItems cartItems = cartItemsRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found")));
        DtoCartItems dtoCartItems = cartItemsMapper.mapEntityToDto(cartItems);

        cartItemsRepository.deleteById(id);
        return dtoCartItems;
    }


    @Override
    public DtoCartItems getCartItemsById(Long id) {
        Optional<CartItems> cartItems = cartItemsRepository.findById(id);
        if (cartItems.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found"));
        }
        return cartItemsMapper.mapEntityToDto(cartItems.get());
    }

    @Override
    public List<DtoCartItems> getUserCartItems(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found"));
        }
        List<CartItems> cartItems = cartItemsRepository.findByUserId(userId);
        return cartItemsMapper.mapEntityListToDtoList(cartItems);
    }
}
