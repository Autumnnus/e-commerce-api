package com.kadir.modules.order.service.impl;

import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import com.kadir.common.enums.OrderStatus;
import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.common.utils.pagination.PageableHelper;
import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.address.repository.AddressRepository;
import com.kadir.modules.authentication.model.Customer;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.CustomerRepository;
import com.kadir.modules.authentication.repository.UserRepository;
import com.kadir.modules.cartitems.model.CartItems;
import com.kadir.modules.cartitems.repository.CartItemsRepository;
import com.kadir.modules.order.dto.OrderDto;
import com.kadir.modules.order.dto.OrderDtoIU;
import com.kadir.modules.order.model.Order;
import com.kadir.modules.order.repository.OrderRepository;
import com.kadir.modules.order.service.IOrderService;
import com.kadir.modules.orderitems.dto.OrderItemsDto;
import com.kadir.modules.orderitems.model.OrderItems;
import com.kadir.modules.orderitems.repository.OrderItemsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final CartItemsRepository cartItemsRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Value("${iyzico.api.key}")
    private String apiKey;

    @Value("${iyzico.secret.key}")
    private String secretKey;

    @Transactional
    @Override
    public OrderDto createOrder(OrderDtoIU dto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Customer customer = customerRepository.findByUserId(userId).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Customer not found")));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));
        List<CartItems> cartItems = cartItemsRepository.findByUserId(customer.getId());
        if (cartItems.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.CARD_IS_EMPTY, cartItems.size() + " item(s)"));
        }

        //? Iyzipay payment response
        Payment payment = createPayment(new CreatePaymentRequest(), user);
//        if (!payment.getErrorCode().isEmpty() || !payment.getErrorMessage().isEmpty()) {
//            throw new BaseException(new ErrorMessage(MessageType.PAYMENT_FAILED, payment.getErrorMessage()));
//        }

        Order order = createAndSaveOrder(customer, cartItems);
        List<OrderItems> savedOrderItems = createAndSaveOrderItems(order, cartItems);

        if (!cartItems.isEmpty()) {
            cartItemsRepository.deleteAll(cartItems);
        }
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setOrderItems(savedOrderItems.stream()
                .map(item -> modelMapper.map(item, OrderItemsDto.class))
                .collect(Collectors.toSet()));
        return orderDto;
    }


    @Override
    public RestPageableEntity<OrderDto> getAllOrders(RestPageableRequest request) {
        Pageable pageable = PageableHelper
                .createPageable(request.getPageNumber(), request.getPageSize(), request.getSortBy(), request.isAsc());
        Page<Order> productPage = orderRepository.findAll(pageable);

        RestPageableEntity<OrderDto> pageableResponse = PaginationUtils.toPageableResponse(productPage, OrderDto.class, modelMapper);

        return pageableResponse;
    }

    @Override
    public RestPageableEntity<OrderDto> getOrdersByUser(Long customerId, RestPageableRequest request) {
        Pageable pageable = PageableHelper
                .createPageable(request.getPageNumber(), request.getPageSize(), request.getSortBy(), request.isAsc());
        customerRepository.findById(customerId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Customer not found")));
        Page<Order> orderPage = orderRepository.findByCustomerId(customerId, pageable);
        RestPageableEntity<OrderDto> pageableResponse = PaginationUtils.toPageableResponse(orderPage, OrderDto.class, modelMapper);
        return pageableResponse;
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderStatus paymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));
        order.setPaymentStatus(paymentStatus);
        Order savedOrder = orderRepository.save(order);

        OrderDto orderDto = modelMapper.map(savedOrder, OrderDto.class);
        orderDto.setOrderItems(savedOrder.getOrderItems().stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemsDto.class))
                .collect(Collectors.toSet()));

        return orderDto;
    }

    private Order createAndSaveOrder(Customer customer, List<CartItems> cartItems) {
        Order order = new Order();
        order.setCustomer(customer);
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

    private Payment createPayment(CreatePaymentRequest request, User user) {
        List<CartItems> cartItems = cartItemsRepository.findByUserId(user.getId());
        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Customer not found")));
        List<com.kadir.modules.address.model.Address> addresses = addressRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Address not found")));
        if (addresses == null || addresses.isEmpty() || addresses.size() == 0) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Address not found"));
        }
        com.kadir.modules.address.model.Address firstAddress = addresses.get(0);
        String fullName = customer.getFirstName() + " " + customer.getLastName();

        Options options = new Options();
        options.setApiKey(apiKey);
        options.setSecretKey(secretKey);
        options.setBaseUrl("https://sandbox-api.iyzipay.com");

        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);
        request.setBasketId("B67832");
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());
        request.setLocale(Locale.EN.getValue());

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(fullName);
        paymentCard.setCardNumber("5528790000000008");
        paymentCard.setExpireMonth("12");
        paymentCard.setExpireYear("2030");
        paymentCard.setCvc("123");
        paymentCard.setRegisterCard(0);
        request.setPaymentCard(paymentCard);

        Buyer buyer = new Buyer();
        buyer.setId(user.getId().toString());
        buyer.setName(customer.getFirstName());
        buyer.setSurname(customer.getLastName());
        buyer.setIdentityNumber("74300864791");
        buyer.setRegistrationAddress(firstAddress.getStreet());
        buyer.setGsmNumber(user.getPhoneNumber());
        buyer.setEmail(user.getEmail());
        buyer.setCity(firstAddress.getCity());
        buyer.setCountry("Turkey");
        buyer.setZipCode("34732");
        request.setBuyer(buyer);

        Address shippingAddress = new Address();
        shippingAddress.setContactName(fullName);
        shippingAddress.setCity(firstAddress.getCity());
        shippingAddress.setCountry(firstAddress.getCountry());
        shippingAddress.setAddress(firstAddress.getStreet());
        shippingAddress.setZipCode(firstAddress.getZipCode());
        request.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setContactName(fullName);
        billingAddress.setCity(firstAddress.getCity());
        billingAddress.setCountry(firstAddress.getCountry());
        billingAddress.setAddress(firstAddress.getStreet());
        billingAddress.setZipCode(firstAddress.getZipCode());
        request.setBillingAddress(billingAddress);

        List<BasketItem> basketItems = getBasketItems(cartItems);

        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        request.setBasketItems(basketItems);
//        request.setPrice(basketItems.stream().map(BasketItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        request.setPaidPrice(request.getPrice());
        request.setPrice(totalAmount);

        Payment payment = Payment.create(request, options);
        return payment;
    }

    private static List<BasketItem> getBasketItems(List<CartItems> cartItems) {
        List<BasketItem> basketItems = new ArrayList<>();

        for (CartItems cartItem : cartItems) {
            BasketItem basketItem = new BasketItem();
            basketItem.setId(cartItem.getId().toString());
            basketItem.setName(cartItem.getProduct().getName());
            basketItem.setCategory1(cartItem.getProduct().getCategory().getName());
            basketItem.setItemType(BasketItemType.PHYSICAL.name());
            basketItem.setPrice(cartItem.getProduct().getPrice());
            basketItems.add(basketItem);
        }
        return basketItems;
    }

}
