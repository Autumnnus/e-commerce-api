package com.kadir.common.dto.openai;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Choice {
    private Message message;
    private String finish_reason;
    private Integer index;
}
