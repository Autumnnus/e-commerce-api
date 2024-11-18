package com.kadir.utils.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestPageableRequest {

    private int pageNumber = 0;

    private int pageSize = 10;

    private String sortBy = "createdAt";

    private boolean asc = true;
}
