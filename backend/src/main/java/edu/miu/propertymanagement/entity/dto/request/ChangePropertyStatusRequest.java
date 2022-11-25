package edu.miu.propertymanagement.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePropertyStatusRequest {
    PropertyAction action;
}

