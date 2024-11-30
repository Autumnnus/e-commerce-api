package com.kadir.modules.address.service.impl;

import com.kadir.common.utils.pagination.PaginationUtils;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.address.dto.DtoAddress;
import com.kadir.modules.address.dto.DtoAddressIU;
import com.kadir.modules.address.model.Address;
import com.kadir.modules.address.repository.AddressRepository;
import com.kadir.modules.address.service.IAddressService;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import jakarta.transaction.Transactional;
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
public class AddressService implements IAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public DtoAddress createAddress(DtoAddressIU dtoAddressIU) {
        User user = userRepository.findById(dtoAddressIU.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Address address = modelMapper.map(dtoAddressIU, Address.class);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, DtoAddress.class);
    }


    @Override
    public DtoAddress updateAddress(Long id, DtoAddressIU dtoAddressIU) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        User user = userRepository.findById(dtoAddressIU.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Address address = modelMapper.map(dtoAddressIU, Address.class);
        address.setId(existingAddress.getId());
        address.setCreatedAt(existingAddress.getCreatedAt());
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, DtoAddress.class);
    }

    @Transactional
    @Override
    public DtoAddress deleteAddress(Long id) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        Address address = modelMapper.map(existingAddress, Address.class);
        addressRepository.deleteById(id);
        DtoAddress dtoAddress = modelMapper.map(address, DtoAddress.class);
        return dtoAddress;
    }

    @Override
    public List<DtoAddress> getUserAddresses(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Address> addresses = addressRepository.findByUserId(userId);
        List<DtoAddress> dtoAddresses = addresses.stream()
                .map(address -> modelMapper.map(address, DtoAddress.class))
                .collect(Collectors.toList());

        return dtoAddresses;
    }


    @Override
    public RestPageableEntity<DtoAddress> getAllAddress(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        Page<Address> addressPage = addressRepository.findAll(pageable);
        RestPageableEntity<DtoAddress> pageableResponse = PaginationUtils.toPageableResponse(addressPage, DtoAddress.class, modelMapper);
        pageableResponse.setDocs(addressPage.getContent().stream()
                .map(product -> modelMapper.map(product, DtoAddress.class))
                .collect(Collectors.toList()));
        return pageableResponse;
    }
}
