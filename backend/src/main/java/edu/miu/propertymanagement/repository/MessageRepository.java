package edu.miu.propertymanagement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import edu.miu.propertymanagement.entity.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("select m from messages m where m.receiver=:id or m.sender=:id")
    List<Message> getAllMessagesByUserId(long id);

    @Query("select m from messages m where m.property=:id")
    List<Message> getAllMessageForProperty(long id);
}
