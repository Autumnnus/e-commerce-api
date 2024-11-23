package com.kadir.service.impl;

import com.kadir.dto.DtoOrder;
import com.kadir.dto.DtoOrderIU;
import com.kadir.dto.DtoOrderItems;
import com.kadir.enums.OrderStatus;
import com.kadir.exception.BaseException;
import com.kadir.exception.ErrorMessage;
import com.kadir.exception.MessageType;
import com.kadir.mapper.OrderMapper;
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
    private OrderMapper orderMapper;

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
        DtoOrder dtoOrder = new DtoOrder();
        List<CartItems> cartItems = getCartItemsByUser(user);
        Set<OrderItems> orderItems = getOrderItemsByCartItems(cartItems);
        Order order = createAndSaveOrder(user, cartItems, orderItems);
        List<OrderItems> savedOrderItems = createAndSaveOrderItems(order, cartItems);
        clearCart(cartItems);
        DtoOrder mappedDtoOrder = orderMapper.mapEntityToDto(order, savedOrderItems);
        BeanUtils.copyProperties(mappedDtoOrder, dtoOrder);
        dtoOrder.setOrderItems(savedOrderItems.stream()
                .map(this::mapOrderItemToDto)
                .collect(Collectors.toSet()));
        return dtoOrder;
    }

    @Override
    public RestPageableEntity<DtoOrder> getAllOrders(int pageNumber, int pageSize) {
        DtoOrder dtoOrder = new DtoOrder();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Order> productPage = orderRepository.findAll(pageable);
        RestPageableEntity<DtoOrder> pageableResponse = PaginationUtils.toPageableResponse(productPage, DtoOrder.class);
        pageableResponse.setDocs(productPage.getContent().stream()
                .map(order -> {
                    DtoOrder mappedDtoOrder = orderMapper.mapEntityToDto(order);
                    BeanUtils.copyProperties(mappedDtoOrder, dtoOrder);
                    dtoOrder.setOrderItems(order.getOrderItems().stream()
                            .map(this::mapOrderItemToDto)
                            .collect(Collectors.toSet()));
//                    dtoOrder.setCreatedDate(order.getCreatedAt());
//                    dtoOrder.setUpdatedDate(order.getUpdatedAt());
                    BeanUtils.copyProperties(order, dtoOrder);
                    return dtoOrder;
                })
                .collect(Collectors.toList()));
        return pageableResponse;
    }


    @Override
    public RestPageableEntity<DtoOrder> getOrdersByUser(Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found"));
        }
        Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);
        RestPageableEntity<DtoOrder> pageableResponse = PaginationUtils.toPageableResponse(orderPage, DtoOrder.class);
        pageableResponse.setDocs(orderPage.getContent().stream()
                .map(order -> {
                    DtoOrder dtoOrder = new DtoOrder();
                    DtoOrder mappedDtoOrder = orderMapper.mapEntityToDto(order);
                    BeanUtils.copyProperties(mappedDtoOrder, dtoOrder);
                    dtoOrder.setOrderItems(order.getOrderItems().stream()
                            .map(this::mapOrderItemToDto)
                            .collect(Collectors.toSet()));
                    BeanUtils.copyProperties(order, dtoOrder);
                    return dtoOrder;
                })
                .collect(Collectors.toList()));
        return pageableResponse;
    }


    @Override
    public DtoOrder updateOrderStatus(Long orderId, OrderStatus paymentStatus) {
        DtoOrder dtoOrder = new DtoOrder();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Sipariş bulunamadı."));
        order.setPaymentStatus(paymentStatus);
        Order savedOrder = orderRepository.save(order);

        DtoOrder mapEntityToDto = orderMapper.mapEntityToDto(savedOrder);
        BeanUtils.copyProperties(mapEntityToDto, dtoOrder);
        dtoOrder.setOrderItems(savedOrder.getOrderItems().stream()
                .map(this::mapOrderItemToDto)
                .collect(Collectors.toSet()));
        BeanUtils.copyProperties(savedOrder, dtoOrder);
        return dtoOrder;
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found")));
    }

    private DtoOrderItems mapOrderItemToDto(OrderItems orderItem) {
        DtoOrderItems dtoOrderItems = new DtoOrderItems();
        BeanUtils.copyProperties(orderItem, dtoOrderItems);
        return dtoOrderItems;
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
        return null;
    }

    @Override
    protected Order mapDtoToEntity(DtoOrderIU dto, Order existingEntity) {
        return null;
    }


    @Override
    protected DtoOrder mapEntityToDto(Order entity) {
        return null;
    }

}
