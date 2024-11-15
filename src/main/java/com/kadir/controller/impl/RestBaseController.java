package com.kadir.controller.impl;

import com.kadir.controller.RootEntity;

public class RestBaseController {

    public <T> RootEntity<T> ok(T payload) {
        return RootEntity.success(payload);
    }

    public <T> RootEntity<T> error(String errorMessage) {
        return RootEntity.error(errorMessage);
    }
}
