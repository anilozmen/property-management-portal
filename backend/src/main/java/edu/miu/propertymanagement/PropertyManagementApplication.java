package edu.miu.propertymanagement;

import edu.miu.propertymanagement.entity.Offer;
import edu.miu.propertymanagement.entity.dto.response.OfferDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PropertyManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyManagementApplication.class, args);
    }
}
