package edu.miu.propertymanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import edu.miu.propertymanagement.entity.Property;
import edu.miu.propertymanagement.entity.User;
import edu.miu.propertymanagement.entity.dto.request.CustomerMessageRequest;
import edu.miu.propertymanagement.entity.dto.request.OwnerMessageRequest;
import edu.miu.propertymanagement.entity.dto.response.MessageDto;
import edu.miu.propertymanagement.service.MessageService;
import edu.miu.propertymanagement.service.PropertyService;
import edu.miu.propertymanagement.service.UserService;
import edu.miu.propertymanagement.service.impl.ApplicationUserDetail;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
@CrossOrigin(origins = "http://localhost:3000")
class MessageController {

    private final UserService userService;

    private final MessageService messageService;

    private final PropertyService propertyService;

    @GetMapping
    public List<MessageDto> getMessageList(@RequestParam(required = false, name = "property_id") Long propertyId) {

        ApplicationUserDetail userDetail = userService.getLoggedInUser();

        if (propertyId != null) {
            if (userDetail.isOwner()) {
                return messageService.getAllMessageForProperty(propertyId, userDetail.getId());
            } else if (userDetail.isCustomer()) {
                return messageService.getAllOwnerRelatedPropertyMessages(propertyId);
            }
        }

        return messageService.getAllMessagesByUserId(userDetail.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addMessage(@RequestBody CustomerMessageRequest customerMessageRequest) {
        messageService.addMessage(customerMessageRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}")
    public void editMessage(@PathVariable(value = "id") long id, @RequestBody OwnerMessageRequest request) {
        MessageDto messageDto = messageService.getMessageById(id);
        messageDto.setReply(request.getReply());
        messageService.editMessage(messageDto, id);
    }
}
