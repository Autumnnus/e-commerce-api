package com.kadir.modules.address.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.address.dto.DtoAddress;
import com.kadir.modules.address.dto.DtoAddressIU;

import java.util.List;

public interface IRestAddressController {

    RootEntity<DtoAddress> createAddress(DtoAddressIU dtoAddressIU);

    RootEntity<DtoAddress> updateAddress(Long id, DtoAddressIU dtoAddressIU);

    RootEntity<DtoAddress> deleteAddress(Long id);

    RootEntity<List<DtoAddress>> getUserAddresses(Long userId);

    RootEntity<RestPageableEntity<DtoAddress>> getAllAddress(RestPageableRequest request);
}
