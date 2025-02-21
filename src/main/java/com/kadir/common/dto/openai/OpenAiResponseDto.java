package com.kadir.common.dto.openai;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenAiResponseDto {

    private String id;
    private String object;
    private Long created;
    private String model;
    private Object usage;
    private List<Choice> choices;
}
