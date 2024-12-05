package com.kadir.admin.user.service;

import com.kadir.common.utils.pagination.RestPageableEntity;
import com.kadir.modules.authentication.model.User;

public interface IAdminService {

    RestPageableEntity<User> getAllUsers();
}
