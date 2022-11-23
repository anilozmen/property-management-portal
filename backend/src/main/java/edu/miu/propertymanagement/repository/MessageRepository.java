package edu.miu.propertymanagement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import edu.miu.propertymanagement.entity.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("select m from messages m where m.receiver.id=:id or m.sender.id=:id")
    List<Message> getAllMessagesByUserId(long id);

    @Query("select m from messages m where m.property.id=:id")
    List<Message> getAllMessageForProperty(long id);

    @Query("select m from messages m where (m.sender.id=:userId or m.sender.id=:secondUserId" +
            " or m.receiver.id=:userId or m.receiver.id=:secondUserId) and m.property.id=:propertyId")
    List<Message> getAllOwnerRelatedPropertyMessages(Long propertyId, Long userId, Long secondUserId);
}
