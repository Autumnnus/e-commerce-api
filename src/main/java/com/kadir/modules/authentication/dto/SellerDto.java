package com.kadir.modules.authentication.dto;

import com.kadir.common.dto.BaseDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto extends BaseDto {

    @NotNull
    private UserDto user;

    @NotNull
    private String companyName;

}

