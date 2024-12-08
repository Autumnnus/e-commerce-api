package com.kadir.admin.user.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.authentication.model.User;

public interface IAdminUserController {

    ApiResponse<RestPageableEntity<User>> getAllUsers();
}
