package com.kadir.utils.pagination;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class PaginationUtils {

    public static <T, D> RestPageableEntity<D> toPageableResponse(Page<T> page, Class<D> dtoClass) {
        RestPageableEntity<D> restPageableEntity = new RestPageableEntity<>();
        restPageableEntity.setDocs(page.getContent().stream()
                .map(entity -> {
                    try {
                        D dto = dtoClass.getDeclaredConstructor().newInstance();
                        BeanUtils.copyProperties(entity, dto);
                        return dto;
                    } catch (Exception e) {
                        throw new RuntimeException("Error while mapping entity to DTO", e);
                    }
                })
                .collect(Collectors.toList()));

        restPageableEntity.setPageNumber(page.getPageable().getPageNumber());
        restPageableEntity.setPageSize(page.getPageable().getPageSize());
        restPageableEntity.setTotalDocs(page.getTotalElements());

        return restPageableEntity;
    }
}
