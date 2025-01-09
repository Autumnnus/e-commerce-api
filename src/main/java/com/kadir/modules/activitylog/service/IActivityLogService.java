package com.kadir.modules.activitylog.service;

import com.kadir.modules.activitylog.enums.LogStatus;

public interface IActivityLogService {

    void logActivity(String action, LogStatus status, String entityType);
}
