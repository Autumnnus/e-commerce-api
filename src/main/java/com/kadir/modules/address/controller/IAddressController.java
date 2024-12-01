package com.kadir.modules.address.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.address.dto.AddressDto;
import com.kadir.modules.address.dto.AddressDtoIU;

import java.util.List;

public interface IAddressController {

    RootEntity<AddressDto> createAddress(AddressDtoIU addressDtoIU);

    RootEntity<AddressDto> updateAddress(Long id, AddressDtoIU addressDtoIU);

    RootEntity<AddressDto> deleteAddress(Long id);

    RootEntity<List<AddressDto>> getUserAddresses(Long userId);

    RootEntity<RestPageableEntity<AddressDto>> getAllAddress(RestPageableRequest request);
}
