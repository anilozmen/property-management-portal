package edu.miu.propertymanagement.service;

import java.util.List;

import edu.miu.propertymanagement.entity.Message;
import edu.miu.propertymanagement.entity.dto.request.CustomerMessageRequest;
import edu.miu.propertymanagement.entity.dto.response.MessageDto;

public interface MessageService {
    List<MessageDto> getAllMessagesByUserId(long userId);
    
    List<MessageDto> getAllMessageForProperty(long id,long userId);
    
    void addMessage(CustomerMessageRequest message);
    
    void editMessage(MessageDto message,long id);
    
    void deleteMessage(long id);

    MessageDto getMessageById(long id);

    List<MessageDto> getAllOwnerRelatedPropertyMessages(Long propertyId);
}
