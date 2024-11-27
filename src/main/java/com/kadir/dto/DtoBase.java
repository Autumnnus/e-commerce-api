package com.kadir.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtoBase {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
