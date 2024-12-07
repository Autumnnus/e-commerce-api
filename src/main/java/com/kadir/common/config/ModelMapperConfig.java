package com.kadir.common.config;

import com.kadir.modules.product.dto.ProductCreateDto;
import com.kadir.modules.product.model.Product;
import org.hibernate.collection.spi.PersistentSet;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(PersistentSet.class, Set.class).setConverter(context -> {
            if (context.getSource() == null) {
                return null;
            }
            return new HashSet<>(context.getSource());
        });

        modelMapper.addMappings(new PropertyMap<ProductCreateDto, Product>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        return modelMapper;
    }
}
