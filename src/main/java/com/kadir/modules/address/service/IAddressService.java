package com.kadir.modules.address.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.address.dto.DtoAddress;
import com.kadir.modules.address.dto.DtoAddressIU;

import java.util.List;

public interface IAddressService {

    DtoAddress createAddress(DtoAddressIU dtoAddressIU);

    DtoAddress updateAddress(Long id, DtoAddressIU dtoAddressIU);

    DtoAddress deleteAddress(Long id);

    List<DtoAddress> getUserAddresses(Long userId);

    RestPageableEntity<DtoAddress> getAllAddress(int pageNumber, int pageSize);
}
