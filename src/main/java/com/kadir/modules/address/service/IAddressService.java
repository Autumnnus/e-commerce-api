package com.kadir.modules.address.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.address.dto.AddressCreateDto;
import com.kadir.modules.address.dto.AddressDto;
import com.kadir.modules.address.dto.AddressUpdateDto;

import java.util.List;

public interface IAddressService {

    AddressDto createAddress(AddressCreateDto addressCreateDto);

    AddressDto updateAddress(Long id, AddressUpdateDto addressUpdateDto);

    AddressDto deleteAddress(Long id);

    List<AddressDto> getUserAddresses(Long userId);

    RestPageableEntity<AddressDto> getAllAddress(int pageNumber, int pageSize);
}
