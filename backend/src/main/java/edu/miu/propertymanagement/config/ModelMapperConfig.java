package edu.miu.propertymanagement.config;

import edu.miu.propertymanagement.entity.Property;
import edu.miu.propertymanagement.entity.dto.response.ListingPropertyDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Property.class, ListingPropertyDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getPropertyAttributes(), ListingPropertyDto::setPropertyAttributesBasicDto);
                });

        return modelMapper;
    }
}
