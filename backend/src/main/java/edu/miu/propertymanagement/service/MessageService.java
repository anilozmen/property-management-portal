package edu.miu.propertymanagement.service;

import java.util.List;

import edu.miu.propertymanagement.entity.Message;
import edu.miu.propertymanagement.entity.dto.response.MessageDto;

public interface MessageService {
    List<MessageDto> getAllMessagesByUserId(long userId);
    
    List<MessageDto> getAllMessageForProperty(long id);
    
    void addMessage(MessageDto message);
    
    void editMessage(MessageDto message,long id);
    
    void deleteMessage(long id);
}
