package com.kadir.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoCartItems extends DtoBase {

    private DtoUser user;

    private DtoProduct product;

    private int quantity;

}
