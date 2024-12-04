package com.kadir.modules.admin.service;

import com.kadir.modules.authentication.model.User;

import java.util.List;

public interface IAdminService {

    List<User> getAllUsers();
}
