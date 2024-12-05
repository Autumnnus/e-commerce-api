package com.kadir.common.utils.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableHelper {

    public static Pageable createPageable(int pageNumber, int pageSize, String sortBy, boolean isAsc) {
        return PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(isAsc ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy))
        );
    }
}
