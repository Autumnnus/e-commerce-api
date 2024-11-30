package com.kadir.common.utils.pagination;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestPageableEntity<T> {

    private List<T> docs;

    private int pageNumber;

    private int pageSize;

    private Long totalDocs;
}
