package com.kadir.common.controller.impl;

import com.kadir.common.controller.RootEntity;

public class RestBaseController {

    public <T> RootEntity<T> ok(T payload) {
        return RootEntity.success(payload);
    }

    public <T> RootEntity<T> error(String errorMessage) {
        return RootEntity.error(errorMessage);
    }
}
