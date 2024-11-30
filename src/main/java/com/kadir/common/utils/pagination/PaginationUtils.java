package com.kadir.common.utils.pagination;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class PaginationUtils {

    public static <T, D> RestPageableEntity<D> toPageableResponse(Page<T> page, Class<D> dtoClass, ModelMapper modelMapper) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        RestPageableEntity<D> restPageableEntity = new RestPageableEntity<>();
        restPageableEntity.setDocs(page.getContent().stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList()));
        restPageableEntity.setPageNumber(page.getPageable().getPageNumber());
        restPageableEntity.setPageSize(page.getPageable().getPageSize());
        restPageableEntity.setTotalDocs(page.getTotalElements());

        return restPageableEntity;
    }
}
