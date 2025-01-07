package com.kadir.modules.discount.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.common.utils.pagination.PageableHelper;
import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import com.kadir.modules.category.model.Category;
import com.kadir.modules.category.repository.CategoryRepository;
import com.kadir.modules.discount.dto.DiscountCreateDto;
import com.kadir.modules.discount.dto.DiscountDto;
import com.kadir.modules.discount.model.Discount;
import com.kadir.modules.discount.repository.DiscountRepository;
import com.kadir.modules.discount.service.IDiscountService;
import com.kadir.modules.product.model.Product;
import com.kadir.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService implements IDiscountService {

    private final ModelMapper modelMapper;
    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public DiscountDto createDiscount(DiscountCreateDto discountCreateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "User not found")));
        Discount discount = modelMapper.map(discountCreateDto, Discount.class);
        if (discountCreateDto.getProductId() != null) {
            Product product = productRepository.findById(discountCreateDto.getProductId())
                    .orElseThrow(() -> new BaseException(
                            new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")));
            discount.setProduct(product);
        }
        if (discountCreateDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(discountCreateDto.getCategoryId())
                    .orElseThrow(() -> new BaseException(
                            new ErrorMessage(MessageType.NO_RECORD_EXIST, "Category not found")));
            discount.setCategory(category);
        }
        discount.setUser(user);
        Discount savedDiscount = discountRepository.save(discount);
        return modelMapper.map(savedDiscount, DiscountDto.class);
    }

    @Override
    public DiscountDto deleteDiscount(Long discountId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Discount not found")));
        DiscountDto discountDto = modelMapper.map(discount, DiscountDto.class);
        discountRepository.delete(discount);
        return discountDto;
    }

    @Override
    public RestPageableEntity<DiscountDto> getAllDiscounts(RestPageableRequest request) {
        Pageable pageable = PageableHelper
                .createPageable(request.getPageNumber(), request.getPageSize(), request.getSortBy(),
                        request.isAsc());
        Page<Discount> discountPage = discountRepository.findAll(pageable);
        RestPageableEntity<DiscountDto> pageableResponse = PaginationUtils.toPageableResponse(discountPage,
                DiscountDto.class, modelMapper);
        return pageableResponse;
    }

    @Override
    public DiscountDto getDiscountByProductId(Long productId) {
        Discount discount = discountRepository.findByProductId(productId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Discount not found")));
        return modelMapper.map(discount, DiscountDto.class);
    }

    @Override
    public DiscountDto getDiscountByCategoryId(Long categoryId) {
        Discount discount = discountRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Discount not found")));
        return modelMapper.map(discount, DiscountDto.class);
    }
}
