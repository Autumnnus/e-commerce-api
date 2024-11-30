package com.kadir.modules.address.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.common.utils.pagination.RestPageableRequest;
import com.kadir.modules.address.controller.IRestAddressController;
import com.kadir.modules.address.dto.DtoAddress;
import com.kadir.modules.address.dto.DtoAddressIU;
import com.kadir.modules.address.service.IAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/address")
public class RestAddressController extends RestBaseController implements IRestAddressController {

    @Autowired
    private IAddressService addressService;

    @PostMapping("/create")
    @Override
    public RootEntity<DtoAddress> createAddress(@RequestBody @Valid DtoAddressIU dtoAddressIU) {
        return ok(addressService.createAddress(dtoAddressIU));
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<DtoAddress> updateAddress(@PathVariable(name = "id") Long id, @RequestBody @Valid DtoAddressIU dtoAddressIU) {
        return ok(addressService.updateAddress(id, dtoAddressIU));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<DtoAddress> deleteAddress(@PathVariable(name = "id") Long id) {
        return ok(addressService.deleteAddress(id));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<List<DtoAddress>> getUserAddresses(@PathVariable(name = "id") Long userId) {
        return ok(addressService.getUserAddresses(userId));
    }

    @GetMapping("/all")
    @Override
    public RootEntity<RestPageableEntity<DtoAddress>> getAllAddress(RestPageableRequest request) {
        return ok(addressService.getAllAddress(request.getPageNumber(), request.getPageSize()));
    }
}
