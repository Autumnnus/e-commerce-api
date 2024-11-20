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
import java.util.Set;
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
        User user = getUserById(dto.getUserId());
        List<CartItems> cartItems = getCartItemsByUser(user);
        Set<OrderItems> orderItems = getOrderItemsByCartItems(cartItems);
        Order order = createAndSaveOrder(user, cartItems, orderItems);
        createAndSaveOrderItems(order, cartItems);
        clearCart(cartItems);
        return mapEntityToDto(order);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found"))
        );
    }

    private Set<OrderItems> getOrderItemsByCartItems(List<CartItems> cartItems) {
        return cartItems.stream()
                .map(cartItem -> {
                    OrderItems item = new OrderItems();
//                    item.setOrder(order);
                    item.setProduct(cartItem.getProduct());
                    item.setQuantity(cartItem.getQuantity());
                    item.setCreatedAt(LocalDateTime.now());
                    item.setUpdatedAt(LocalDateTime.now());
                    item.setPrice(cartItem.getProduct().getPrice());
                    return item;
                }).collect(Collectors.toSet());
    }

    private List<CartItems> getCartItemsByUser(User user) {
        List<CartItems> cartItems = cartItemsRepository.findByUserId(user.getId());
        if (cartItems.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Cart is empty"));
        }
        return cartItems;
    }

    private Order createAndSaveOrder(User user, List<CartItems> cartItems, Set<OrderItems> orderItems) {
        Order order = new Order();
        order.setUser(user);
//        order.setOrderItems(orderItems);
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod("CREDIT CARD");
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    private void createAndSaveOrderItems(Order order, List<CartItems> cartItems) {
        List<OrderItems> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItems item = new OrderItems();
                    item.setOrder(order);
                    item.setProduct(cartItem.getProduct());
                    item.setQuantity(cartItem.getQuantity());
                    item.setCreatedAt(LocalDateTime.now());
                    item.setUpdatedAt(LocalDateTime.now());
                    item.setPrice(cartItem.getProduct().getPrice());
                    return item;
                }).collect(Collectors.toList());
        orderItemsRepository.saveAll(orderItems);
    }

    private void clearCart(List<CartItems> cartItems) {
        if (!cartItems.isEmpty()) {
            cartItemsRepository.deleteAll(cartItems);
        }
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
        return orderRepository;
    }

    @Override
    protected Order mapDtoToEntity(DtoOrderIU dto, Order existingEntity) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found"))
        );

        existingEntity.setCreatedAt(LocalDateTime.now());
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity.setUser(user);
//        existingEntity.setOrderItems(orderItems);
        //TODO orderItems boş geliyor
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
//        if (entity.getOrderItems() == null) {
//            DtoOrderItems dtoOrderItems = new DtoOrderItems();
//            BeanUtils.copyProperties(entity.getOrderItems(), dtoOrderItems);
//            dto.setOrderItems(Set.of(dtoOrderItems));
//        }
        return dto;
    }
}
