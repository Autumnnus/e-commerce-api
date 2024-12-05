package com.kadir.admin.user.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.authentication.model.User;

import java.util.List;

public interface IAdminUserController {

    RootEntity<List<User>> getAllUsers();
}
