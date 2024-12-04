package com.kadir.modules.admin.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.authentication.model.User;

import java.util.List;

public interface IAdminController {

    RootEntity<List<User>> getAllUsers();
}
