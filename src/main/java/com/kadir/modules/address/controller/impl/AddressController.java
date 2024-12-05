package com.kadir.modules.address.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.address.controller.IAddressController;
import com.kadir.modules.address.dto.AddressCreateDto;
import com.kadir.modules.address.dto.AddressDto;
import com.kadir.modules.address.dto.AddressUpdateDto;
import com.kadir.modules.address.service.IAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/address")
public class AddressController extends RestBaseController implements IAddressController {

    @Autowired
    private IAddressService addressService;

    @PostMapping
    @Override
    public RootEntity<AddressDto> createAddress(@RequestBody @Valid AddressCreateDto addressCreateDto) {
        return ok(addressService.createAddress(addressCreateDto));
    }

    @PutMapping(path = "/{id}")
    @Override
    public RootEntity<AddressDto> updateAddress(@PathVariable(name = "id") Long id, @RequestBody @Valid AddressUpdateDto addressUpdateDto) {
        return ok(addressService.updateAddress(id, addressUpdateDto));
    }

    @DeleteMapping("/{id}")
    @Override
    public RootEntity<AddressDto> deleteAddress(@PathVariable(name = "id") Long id) {
        return ok(addressService.deleteAddress(id));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<List<AddressDto>> getUserAddresses(@PathVariable(name = "id") Long userId) {
        return ok(addressService.getUserAddresses(userId));
    }

    @GetMapping
    @Override
    public RootEntity<RestPageableEntity<AddressDto>> getAllAddress(RestPageableRequest request) {
        return ok(addressService.getAllAddress(request));
    }
}
