package edu.miu.propertymanagement.entity.dto.response;

import java.time.LocalDateTime;

import edu.miu.propertymanagement.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private long id;
    private long receiverId;
    private long senderId;
    private String reply;
    private LocalDateTime createdDate;
}
