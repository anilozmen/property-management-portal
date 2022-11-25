package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.service.NotificationService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import edu.miu.propertymanagement.entity.Message;
import edu.miu.propertymanagement.entity.dto.request.CustomerMessageRequest;
import edu.miu.propertymanagement.entity.dto.response.MessageDto;
import edu.miu.propertymanagement.mapper.MessageMapper;
import edu.miu.propertymanagement.repository.MessageRepository;
import edu.miu.propertymanagement.service.MessageService;
import edu.miu.propertymanagement.service.PropertyService;
import edu.miu.propertymanagement.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    private final PropertyService propertyService;

    private final NotificationService notificationService;

    @Override
    public List<MessageDto> getAllMessagesByUserId(long userId) {
        List<Message> messages = messageRepository.getAllMessagesByUserId(userId);
        return MessageMapper.mapMessageListToDtoList(messages);
    }

    @Override
    public List<MessageDto> getAllMessageForProperty(long id, long userId) {
        List<Message> messages = messageRepository.getAllMessageForProperty(id, userId);
        return MessageMapper.mapMessageListToDtoList(messages);
    }

    @Override
    public void addMessage(CustomerMessageRequest customerMessageRequest) {
        ApplicationUserDetail userDetail = userService.getLoggedInUser();

        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(customerMessageRequest.getMessage());
        messageDto.setPropertyId(customerMessageRequest.getPropertyId());
        messageDto.setSenderId(userDetail.getId());
        messageDto.setReceiverId(propertyService.getOwnerByProperty(customerMessageRequest.getPropertyId()));
        messageDto.setCreatedDate(LocalDateTime.now());

        Message message = mapMessageDtoToEntity(messageDto);
        messageRepository.save(message);

        notificationService.sendNotificationForMessage(message);
    }

    @Override
    public void editMessage(MessageDto messageDto, long id) {
        Message message = mapMessageDtoToEntity(messageDto);
        message.setId(id);
        messageRepository.save(message);

        notificationService.sendNotificationForMessage(message);
    }

    @Override
    public void deleteMessage(long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public MessageDto getMessageById(long id) {
        return MessageMapper.mapMessageToDto(messageRepository.findById(id).orElse(null));
    }

    @Override
    public List<MessageDto> getAllOwnerRelatedPropertyMessages(Long propertyId) {
        ApplicationUserDetail userDetail = userService.getLoggedInUser();
        Long loggedInUserId = propertyService.getOwnerByProperty(propertyId);
        return MessageMapper.mapMessageListToDtoList(messageRepository.getAllOwnerRelatedPropertyMessages(
                propertyId,
                userDetail.getId(),
                loggedInUserId

        ));
    }

    private Message mapMessageDtoToEntity(MessageDto messageDto) {
        Message message = new Message();
        message.setReply(messageDto.getReply());
        message.setMessage(messageDto.getMessage());
        message.setProperty(propertyService.getPropertyByIdAndIncrementView(messageDto.getPropertyId(), false));
        message.setReceiver(userService.findById(messageDto.getReceiverId()));
        message.setSender(userService.findById(messageDto.getSenderId()));
        message.setCreatedDate(messageDto.getCreatedDate());
        return message;
    }

}
