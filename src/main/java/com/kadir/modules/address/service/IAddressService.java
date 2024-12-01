package com.kadir.modules.address.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.address.dto.AddressDto;
import com.kadir.modules.address.dto.AddressDtoIU;

import java.util.List;

public interface IAddressService {

    AddressDto createAddress(AddressDtoIU addressDtoIU);

    AddressDto updateAddress(Long id, AddressDtoIU addressDtoIU);

    AddressDto deleteAddress(Long id);

    List<AddressDto> getUserAddresses(Long userId);

    RestPageableEntity<AddressDto> getAllAddress(int pageNumber, int pageSize);
}
