package com.kadir.admin.user.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.authentication.model.User;

public interface IAdminUserController {

    RootEntity<RestPageableEntity<User>> getAllUsers();
}
