package com.kadir.common.aspect;

import com.kadir.modules.activitylog.enums.LogStatus;
import com.kadir.modules.activitylog.service.impl.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final ActivityLogService activityLogService;

    private static final String CONTROLLER_POINTCUT = "execution(* com.kadir.modules..controller.*.*(..)) || execution(* com.kadir.admin..controller.*.*(..))";

    @AfterReturning(pointcut = CONTROLLER_POINTCUT)
    public void logAfterSuccessfulExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String entityName = joinPoint.getSignature().getDeclaringType().getSimpleName();

        activityLogService.logActivity(
                camelToUpperSnake(methodName),
                LogStatus.SUCCESS,
                cleanEntityName(entityName)
        );
    }

    @AfterThrowing(pointcut = CONTROLLER_POINTCUT, throwing = "exception")
    public void logAfterException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String entityName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        activityLogService.logActivity(
                camelToUpperSnake(methodName),
                LogStatus.FAILED,
                cleanEntityName(entityName)
        );
    }

    private static String camelToUpperSnake(String str) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (Character.isUpperCase(ch)) {
                if (i > 0) result.append('_');
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }

        return result.toString().toUpperCase();
    }

    private String cleanEntityName(String entityName) {
        if (entityName != null) {
            if (entityName.endsWith("ControllerImpl")) {
                return entityName.substring(0, entityName.length() - "ControllerImpl".length());
            } else if (entityName.endsWith("Controller")) {
                return entityName.substring(0, entityName.length() - "Controller".length());
            }
        }
        return entityName;
    }


}
