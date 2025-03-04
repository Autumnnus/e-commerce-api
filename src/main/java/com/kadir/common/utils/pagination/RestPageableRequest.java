package com.kadir.common.utils.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestPageableRequest {

    private int pageNumber = 0;

    private int pageSize = 10;

    private String sortBy = "updatedAt";

    private boolean asc = true;
}
