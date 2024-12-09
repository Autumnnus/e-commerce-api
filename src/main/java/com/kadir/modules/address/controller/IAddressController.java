package com.kadir.modules.address.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.address.dto.AddressCreateDto;
import com.kadir.modules.address.dto.AddressDto;
import com.kadir.modules.address.dto.AddressUpdateDto;

import java.util.List;

public interface IAddressController {

    ApiResponse<AddressDto> createAddress(AddressCreateDto addressCreateDto);

    ApiResponse<AddressDto> updateAddress(Long id, AddressUpdateDto addressUpdateDto);

    ApiResponse<AddressDto> deleteAddress(Long id);

    ApiResponse<List<AddressDto>> getUserAddresses(Long userId);

    ApiResponse<RestPageableEntity<AddressDto>> getAllAddress(RestPageableRequest request);
}
