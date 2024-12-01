package com.kadir.modules.category.dto;

import com.kadir.common.dto.DtoBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends DtoBase {

    private String name;

    private String description;
}
