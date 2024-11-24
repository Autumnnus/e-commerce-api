package com.kadir.dto;

import com.kadir.model.Category;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoProduct extends DtoBase {

    private String name;

    private String description;

    private BigDecimal price;

    private int stockQuantity;

    private Category category;
}
