package edu.miu.propertymanagement.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
//    private long id;
    private Boolean deleted;
    private Boolean activated;
}
