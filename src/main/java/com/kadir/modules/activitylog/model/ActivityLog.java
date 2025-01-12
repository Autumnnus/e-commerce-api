package com.kadir.modules.activitylog.model;

import com.kadir.common.model.BaseEntity;
import com.kadir.modules.activitylog.enums.LogStatus;
import com.kadir.modules.authentication.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "activity_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    private String action;

    @Enumerated(EnumType.STRING)
    private LogStatus status;

    private String entityType;

    private String description;

}
