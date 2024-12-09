package com.kadir.common.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
