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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityLogService implements IActivityLogService {

    private final AuthenticationServiceImpl authenticationService;
    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;

    @Override
    public void logActivity(String action, LogStatus status, String entityType, String description) {
        if (action != null && action.length() > 255) {
            action = action.substring(0, 255);
        }
        if (entityType != null && entityType.length() > 255) {
            entityType = entityType.substring(0, 255);
        }
        if (description != null && description.length() > 255) {
            description = description.substring(0, 255);
        }

        Long userId = authenticationService.getCurrentUserId();
        Optional<User> user;
        if (userId == null) {
            user = Optional.empty();
        } else {
            user = userRepository.findById(userId);
        }

        ActivityLog log = new ActivityLog();
        log.setUser(user.orElse(null));
        log.setAction(action);
        log.setStatus(status);
        log.setEntityType(entityType);
        log.setDescription(description);
        activityLogRepository.save(log);
    }
}
