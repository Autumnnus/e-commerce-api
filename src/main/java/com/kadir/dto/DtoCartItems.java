package com.kadir.dto;

import com.kadir.model.Product;
import com.kadir.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCartItems extends DtoBase {

    private User user;

    private Product product;

    private int quantity;

}
