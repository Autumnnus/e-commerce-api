package com.kadir.service.impl;

import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.dto.DtoUser;
import com.kadir.enums.OrderStatus;
import com.kadir.exception.BaseException;
import com.kadir.exception.ErrorMessage;
import com.kadir.exception.MessageType;
import com.kadir.model.CartItems;
import com.kadir.model.Order;
import com.kadir.model.OrderItems;
import com.kadir.model.User;
import com.kadir.repository.CartItemsRepository;
import com.kadir.repository.OrderItemsRepository;
import com.kadir.repository.OrderRepository;
import com.kadir.repository.UserRepository;
import com.kadir.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, DtoOrderIU, DtoOrder> implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public DtoOrder createOrder(DtoOrderIU dto) {
        // List<CartItems> cartItems =
        // cartItemsRepository.findByUserId(dtoOrderIU.getUserId());
        // if (cartItems.isEmpty()) {
        // throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Cart
        // is empty"));
        // }

        Order order = new Order();

        order.setUser(userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found"))));
        order.setOrderDate(java.time.LocalDateTime.now());
        order.setPaymentMethod("CREDIT CARD");
        order.setStatus(OrderStatus.PENDING);
        Optional<User> user = userRepository.findById(dto.getUserId());
        if (user.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found"));
        }
        order.setUser(user.get());

        // Set<Long> orderItemIds = dto.getOrderItems()
        // .stream()
        // .map(Long::valueOf) // String -> Long dönüşümü
        // .collect(Collectors.toSet());

        List<CartItems> cartItems = cartItemsRepository.findByUserId(user.get().getId());
        if (cartItems.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Cart is empty"));
        }

        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        List<OrderItems> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItems item = new OrderItems();
                    item.setOrder(savedOrder);
                    item.setProduct(cartItem.getProduct());
                    item.setQuantity(cartItem.getQuantity());
                    item.setCreatedAt(LocalDateTime.now());
                    item.setUpdatedAt(LocalDateTime.now());
                    item.setPrice(cartItem.getProduct().getPrice());
                    return item;
                }).collect(Collectors.toList());
        orderItemsRepository.saveAll(orderItems);
        if (!cartItems.isEmpty()) {
            cartItemsRepository.deleteAll(cartItems);
        }

        return mapEntityToDto(order);
    }

    @Override
    public List<DtoOrder> getAllOrders() {
        return List.of();
    }

    @Override
    public List<DtoOrder> getOrdersByUser(String userId) {
        return List.of();
    }

    @Override
    public DtoOrder updateOrderStatus(String orderId, String status) {
        return null;
    }

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return null;
    }

    @Override
    protected Order mapDtoToEntity(DtoOrderIU dto, Order existingEntity) {
        if (existingEntity == null) {
            existingEntity = new Order();
            existingEntity.setCreatedAt(LocalDateTime.now());
        }
        existingEntity.setUpdatedAt(LocalDateTime.now());
        // existingEntity.setUser(user.get());
        // existingEntity.setOrderItems(orderItems);
        existingEntity.setOrderDate(LocalDateTime.now());
        existingEntity.setStatus(OrderStatus.PENDING);
        existingEntity.setTotalAmount(BigDecimal.valueOf(100));
        existingEntity.setPaymentMethod("CREDIT CARD");
        return existingEntity;
    }

    @Override
    protected DtoOrder mapEntityToDto(Order entity) {
        DtoOrder dto = new DtoOrder();
        BeanUtils.copyProperties(entity, dto);
        dto.setCreatedDate(entity.getCreatedAt());
        dto.setUpdatedDate(entity.getUpdatedAt());
        if (entity.getUser() != null) {
            DtoUser dtoUser = new DtoUser();
            BeanUtils.copyProperties(entity.getUser(), dtoUser);
            dto.setUser(dtoUser);
        }
        return dto;
    }
}
