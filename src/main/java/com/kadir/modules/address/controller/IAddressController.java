package com.kadir.modules.address.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.address.dto.AddressCreateDto;
import com.kadir.modules.address.dto.AddressDto;
import com.kadir.modules.address.dto.AddressUpdateDto;

import java.util.List;

public interface IAddressController {

    RootEntity<AddressDto> createAddress(AddressCreateDto addressCreateDto);

    RootEntity<AddressDto> updateAddress(Long id, AddressUpdateDto addressUpdateDto);

    RootEntity<AddressDto> deleteAddress(Long id);

    RootEntity<List<AddressDto>> getUserAddresses(Long userId);

    RootEntity<RestPageableEntity<AddressDto>> getAllAddress(RestPageableRequest request);
}
