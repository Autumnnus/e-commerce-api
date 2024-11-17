package com.kadir.dto;

import com.kadir.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoSeller extends DtoBase {

    @NotNull
    private User userId;

    @NotNull
    private String companyName;

}

