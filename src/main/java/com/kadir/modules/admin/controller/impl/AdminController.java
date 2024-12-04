package com.kadir.modules.admin.controller.impl;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.admin.controller.IAdminController;
import com.kadir.modules.admin.service.IAdminService;
import com.kadir.modules.authentication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController extends RestBaseController implements IAdminController {

    @Autowired
    private IAdminService adminService;

    @GetMapping("/users")
    @Override
    public RootEntity<List<User>> getAllUsers() {
        return ok(adminService.getAllUsers());
    }
}
