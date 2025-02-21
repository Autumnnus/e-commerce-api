package com.kadir.modules.address.service.impl;

import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.common.utils.merge.MergeUtils;
import com.kadir.common.utils.pagination.PageableHelper;
import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.address.dto.AddressCreateDto;
import com.kadir.modules.address.dto.AddressDto;
import com.kadir.modules.address.dto.AddressUpdateDto;
import com.kadir.modules.address.model.Address;
import com.kadir.modules.address.repository.AddressRepository;
import com.kadir.modules.address.service.IAddressService;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Override
    public AddressDto createAddress(AddressCreateDto addressCreateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Address address = modelMapper.map(addressCreateDto, Address.class);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDto.class);
    }

    @Override
    public AddressDto updateAddress(Long id, AddressUpdateDto addressUpdateDto) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!existingAddress.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(
                    MessageType.UNAUTHORIZED, "You are not authorized to update this address"));
        }

        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existingAddress.setUser(user);
        }
        MergeUtils.copyNonNullProperties(addressUpdateDto, existingAddress);
        Address savedAddress = addressRepository.save(existingAddress);
        return modelMapper.map(savedAddress, AddressDto.class);
    }

    @Transactional
    @Override
    public AddressDto deleteAddress(Long id) {
        Long userId = authenticationServiceImpl.getCurrentUserId();
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!existingAddress.getUser().getId().equals(userId)) {
            throw new BaseException(new ErrorMessage(
                    MessageType.UNAUTHORIZED, "You are not authorized to delete this address"));
        }

        Address address = modelMapper.map(existingAddress, Address.class);
        addressRepository.deleteById(id);
        AddressDto addressDto = modelMapper.map(address, AddressDto.class);
        return addressDto;
    }

    @Override
    public List<AddressDto> getUserAddresses(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Address> addresses = addressRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Addresses not found"));
        List<AddressDto> addressDtos = addresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .collect(Collectors.toList());

        return addressDtos;
    }


    @Override
    public RestPageableEntity<AddressDto> getAllAddress(RestPageableRequest request) {
        Pageable pageable = PageableHelper
                .createPageable(request.getPageNumber(), request.getPageSize(), request.getSortBy(), request.isAsc());
        Page<Address> addressPage = addressRepository.findAll(pageable);
        RestPageableEntity<AddressDto> pageableResponse = PaginationUtils.toPageableResponse(addressPage, AddressDto.class, modelMapper);
        pageableResponse.setDocs(addressPage.getContent().stream()
                .map(product -> modelMapper.map(product, AddressDto.class))
                .collect(Collectors.toList()));
        return pageableResponse;
    }
}
