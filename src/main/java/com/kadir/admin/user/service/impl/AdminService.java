package com.kadir.admin.user.service.impl;

import com.kadir.admin.user.service.IAdminService;
import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public RestPageableEntity<User> getAllUsers() {
        Pageable pageable = PageRequest.of(0, 100, Sort.by("createdAt").descending());
        Page<User> productPage = userRepository.findAll(pageable);
        RestPageableEntity<User> pageableResponse = PaginationUtils.toPageableResponse(productPage, User.class, modelMapper);
        pageableResponse.setDocs(productPage.getContent().stream()
                .map(product -> modelMapper.map(product, User.class))
                .collect(Collectors.toList()));
        return pageableResponse;
    }
}
