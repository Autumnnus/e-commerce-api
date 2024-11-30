package com.kadir.modules.orderitems.service.impl;

import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.orderitems.dto.DtoOrderItems;
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
    public List<DtoOrderItems> getOrderItemsByOrderId(Long orderId) {
        List<OrderItems> orderItems = orderItemsRepository.findByOrderId(orderId);
        return orderItems.stream()
                .map(item -> modelMapper.map(item, DtoOrderItems.class))
                .collect(Collectors.toList());
    }

    @Override
    public RestPageableEntity<DtoOrderItems> getAllOrderItems(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<OrderItems> orderItemsPage = orderItemsRepository.findAll(pageable);
        RestPageableEntity<DtoOrderItems> pageableResponse = PaginationUtils.toPageableResponse(orderItemsPage, DtoOrderItems.class, modelMapper);
        return pageableResponse;
    }
}
