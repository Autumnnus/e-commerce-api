package com.kadir.service.impl;

import com.kadir.dto.*;
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
import com.kadir.utils.pagination.PaginationUtils;
import com.kadir.utils.pagination.RestPageableEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        List<OrderItems> savedOrderItems = createAndSaveOrderItems(order, cartItems);
        clearCart(cartItems);
        return mapEntityToDto(order, savedOrderItems);
    }

    @Override
    public RestPageableEntity<DtoOrder> getAllOrders(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Order> productPage = orderRepository.findAll(pageable);
        return PaginationUtils.toPageableResponse(productPage, DtoOrder.class);
    }


    @Override
    public RestPageableEntity<DtoOrder> getOrdersByUser(Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found"));
        }
        Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);
        return PaginationUtils.toPageableResponse(orderPage, DtoOrder.class);
    }


    @Override
    public DtoOrder updateOrderStatus(Long orderId, OrderStatus paymentStatus) {
        DtoOrder dtoOrder = new DtoOrder();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Sipariş bulunamadı."));
        order.setPaymentStatus(paymentStatus);
        Order savedOrder = orderRepository.save(order);

        BeanUtils.copyProperties(savedOrder, dtoOrder);
        return mapEntityToDto(savedOrder);
    }

    public List<DtoOrder> listMapEntityToDto(List<Order> order) {
        return order.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found")));
    }

    private Set<OrderItems> getOrderItemsByCartItems(List<CartItems> cartItems) {
        return cartItems.stream()
                .map(cartItem -> {
                    OrderItems item = new OrderItems();
                    // item.setOrder(order);
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
        // order.setOrderItems(orderItems);
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod("CREDIT CARD");
        order.setPaymentStatus(OrderStatus.PENDING);

        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    private List<OrderItems> createAndSaveOrderItems(Order order, List<CartItems> cartItems) {
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
        List<OrderItems> savedOrderItems = orderItemsRepository.saveAll(orderItems);
        return savedOrderItems;
    }

    private void clearCart(List<CartItems> cartItems) {
        if (!cartItems.isEmpty()) {
            cartItemsRepository.deleteAll(cartItems);
        }
    }


    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    protected Order mapDtoToEntity(DtoOrderIU dto, Order existingEntity) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found")));

        existingEntity.setCreatedAt(LocalDateTime.now());
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity.setUser(user);
        existingEntity.setOrderDate(LocalDateTime.now());
        existingEntity.setPaymentStatus(OrderStatus.PENDING);
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

    protected DtoOrder mapEntityToDto(Order entity, List<OrderItems> orderItems) {
        DtoOrder dto = new DtoOrder();
        BeanUtils.copyProperties(entity, dto);
        dto.setCreatedDate(entity.getCreatedAt());
        dto.setUpdatedDate(entity.getUpdatedAt());
        dto.setOrderItems(orderItems.stream().map(orderItem -> {
            DtoOrderItems dtoOrderItems = new DtoOrderItems();
            BeanUtils.copyProperties(orderItem, dtoOrderItems);
            DtoProduct dtoProduct = new DtoProduct();
            //* Eğer Product içerisindeki tüm verileri almak istemiyorsak BeanUtils'i kullanmamalıyız
            // BeanUtils.copyProperties(orderItem.getProduct(), dtoProduct);

            dtoProduct.setId(orderItem.getProduct().getId());
            dtoProduct.setName(orderItem.getProduct().getName());
            dtoOrderItems.setProduct(dtoProduct);
            return dtoOrderItems;
        }).collect(Collectors.toSet()));
        if (entity.getUser() != null) {
            DtoUser dtoUser = new DtoUser();
            BeanUtils.copyProperties(entity.getUser(), dtoUser);
            dto.setUser(dtoUser);
        }
        return dto;
    }
}
