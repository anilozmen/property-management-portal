package edu.miu.propertymanagement.entity.dto.request;

import edu.miu.propertymanagement.entity.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeOfferStatusRequest {
    OfferStatus status;
}
