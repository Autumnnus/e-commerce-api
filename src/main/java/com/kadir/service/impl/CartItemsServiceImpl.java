package com.kadir.service.impl;

import com.kadir.dto.DtoCartItems;
import com.kadir.dto.DtoCartItemsIU;
import com.kadir.dto.DtoProduct;
import com.kadir.dto.DtoUser;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemsServiceImpl extends BaseServiceImpl<CartItems, DtoCartItemsIU, DtoCartItems> implements ICartItemsService {

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected JpaRepository<CartItems, Long> getRepository() {
        return cartItemsRepository;
    }

    @Override
    protected CartItems mapDtoToEntity(DtoCartItemsIU dto, CartItems existingEntity) {
        Optional<Product> product = productRepository.findById(dto.getProductId());
        if (product.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found"));
        }

        Optional<User> user = userRepository.findById(dto.getUserId());
        if (user.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found"));
        }

        if (existingEntity == null) {
            existingEntity = new CartItems();
            existingEntity.setCreatedAt(LocalDateTime.now());
        }
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity.setQuantity(dto.getQuantity());
        existingEntity.setProduct(product.get());
        existingEntity.setUser(user.get());
        return existingEntity;
    }

    @Override
    protected DtoCartItems mapEntityToDto(CartItems entity) {
        DtoCartItems dto = new DtoCartItems();
        BeanUtils.copyProperties(entity, dto);
        dto.setCreatedDate(entity.getCreatedAt());
        dto.setUpdatedDate(entity.getUpdatedAt());
        if (entity.getUser() != null) {
            DtoUser dtoUser = new DtoUser();
            BeanUtils.copyProperties(entity.getUser(), dtoUser);
            dto.setUser(dtoUser);
        }

        if (entity.getProduct() != null) {
            DtoProduct dtoProduct = new DtoProduct();
            BeanUtils.copyProperties(entity.getProduct(), dtoProduct);
            dto.setProduct(dtoProduct);
        }
        return dto;
    }

    public List<DtoCartItems> listMapEntityToDto(List<CartItems> cartItems) {
        return cartItems.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public DtoCartItems createCartItems(DtoCartItemsIU dtoCartItemsIU) {
        CartItems savedCartItems = cartItemsRepository.save(mapDtoToEntity(dtoCartItemsIU, null));
        DtoCartItems dtoCartItems = new DtoCartItems();
        DtoProduct product = new DtoProduct();
        DtoUser user = new DtoUser();
        BeanUtils.copyProperties(savedCartItems, dtoCartItems);
        BeanUtils.copyProperties(savedCartItems.getProduct(), product);
        BeanUtils.copyProperties(savedCartItems.getUser(), user);

        dtoCartItems.setQuantity(savedCartItems.getQuantity());
        dtoCartItems.setCreatedDate(savedCartItems.getCreatedAt());
        dtoCartItems.setUpdatedDate(savedCartItems.getUpdatedAt());
        return dtoCartItems;
    }

    @Override
    public DtoCartItems updateCartItems(Long id, DtoCartItemsIU dtoCartItemsIU) {
        Optional<CartItems> cartItems = cartItemsRepository.findById(id);
        if (cartItems.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found"));
        }

        CartItems existingCartItems = cartItems.get();
        CartItems updatedCartItems = mapDtoToEntity(dtoCartItemsIU, existingCartItems);
        CartItems savedCartItems = cartItemsRepository.save(updatedCartItems);

        DtoCartItems updatedDtoCartItems = new DtoCartItems();
        DtoProduct product = new DtoProduct();
        DtoUser user = new DtoUser();
        BeanUtils.copyProperties(savedCartItems, updatedDtoCartItems);
        BeanUtils.copyProperties(savedCartItems.getProduct(), product);
        BeanUtils.copyProperties(savedCartItems.getUser(), user);
        return updatedDtoCartItems;
    }

    @Override
    public DtoCartItems deleteCartItems(Long id) {
        Optional<CartItems> cartItems = cartItemsRepository.findById(id);

        if (cartItems.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found"));
        }
        DtoCartItems dtoCartItems = new DtoCartItems();

        cartItemsRepository.deleteById(id);
        BeanUtils.copyProperties(cartItems.get(), dtoCartItems);
        dtoCartItems.setCreatedDate(cartItems.get().getCreatedAt());
        dtoCartItems.setUpdatedDate(cartItems.get().getUpdatedAt());
        return dtoCartItems;
    }

    @Override
    public DtoCartItems getCartItemsById(Long id) {
        Optional<CartItems> cartItems = cartItemsRepository.findById(id);
        if (cartItems.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CartItems not found"));
        }
        return mapEntityToDto(cartItems.get());
    }

    @Override
    public List<DtoCartItems> getUserCartItems(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found"));
        }
        List<CartItems> cartItems = cartItemsRepository.findByUserId(userId);
        return listMapEntityToDto(cartItems);
    }
}
