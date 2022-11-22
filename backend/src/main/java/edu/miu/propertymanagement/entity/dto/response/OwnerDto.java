package edu.miu.propertymanagement.entity.dto.response;

import edu.miu.propertymanagement.entity.Property;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private List<Property> properties;
}
