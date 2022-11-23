package edu.miu.propertymanagement.mapper;

import java.util.List;
import java.util.stream.Collectors;

import edu.miu.propertymanagement.entity.Message;
import edu.miu.propertymanagement.entity.dto.response.MessageDto;

public class MessageMapper {

    public static MessageDto mapMessageToDto(Message message) {
        return new MessageDto(message.getId(), message.getReceiver().getId(), message.getSender().getId(), message.getReply(), message.getCreatedDate());
    }

    public static List<MessageDto> mapMessageListToDtoList(List<Message> messages) {
        return messages.stream().map(MessageMapper::mapMessageToDto).collect(Collectors.toList());
    }

}
