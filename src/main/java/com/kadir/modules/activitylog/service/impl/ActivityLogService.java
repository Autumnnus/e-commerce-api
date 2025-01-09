package com.kadir.modules.activitylog.service.impl;

import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.modules.activitylog.enums.LogStatus;
import com.kadir.modules.activitylog.model.ActivityLog;
import com.kadir.modules.activitylog.repository.ActivityLogRepository;
import com.kadir.modules.activitylog.service.IActivityLogService;
import com.kadir.modules.authentication.model.User;
import com.kadir.modules.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLogService implements IActivityLogService {

    private final AuthenticationServiceImpl authenticationService;
    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;

    @Override
    public void logActivity(String action, LogStatus status, String entityType) {
        Long userId = authenticationService.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setAction(action);
        log.setStatus(status);
        log.setEntityType(entityType);
        activityLogRepository.save(log);
    }
}
