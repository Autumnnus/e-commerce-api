package com.kadir.modules.orderitems.service.impl;

import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.orderitems.dto.OrderItemsDto;
import com.kadir.modules.orderitems.model.OrderItems;
import com.kadir.modules.orderitems.repository.OrderItemsRepository;
import com.kadir.modules.orderitems.service.IOrderItemsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemsService implements IOrderItemsService {

    private final OrderItemsRepository orderItemsRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderItemsDto> getOrderItemsByOrderId(Long orderId) {
        List<OrderItems> orderItems = orderItemsRepository.findByOrderId(orderId);
        return orderItems.stream()
                .map(item -> modelMapper.map(item, OrderItemsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RestPageableEntity<OrderItemsDto> getAllOrderItems(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<OrderItems> orderItemsPage = orderItemsRepository.findAll(pageable);
        RestPageableEntity<OrderItemsDto> pageableResponse = PaginationUtils.toPageableResponse(orderItemsPage, OrderItemsDto.class, modelMapper);
        return pageableResponse;
    }
}
