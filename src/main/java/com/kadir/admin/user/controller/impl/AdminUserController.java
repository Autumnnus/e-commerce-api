package com.kadir.admin.user.controller.impl;

import com.kadir.admin.user.controller.IAdminUserController;
import com.kadir.admin.user.service.IAdminService;
import com.kadir.common.controller.RootEntity;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.authentication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/api/user")
public class AdminUserController extends RestBaseController implements IAdminUserController {

    @Autowired
    private IAdminService adminService;

    @GetMapping
    @Override
    public RootEntity<List<User>> getAllUsers() {
        return ok(adminService.getAllUsers());
    }
}
