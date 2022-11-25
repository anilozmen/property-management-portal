package edu.miu.propertymanagement.service;

import edu.miu.propertymanagement.entity.Message;
import edu.miu.propertymanagement.entity.Offer;

public interface NotificationService {
    void sendNotificationForMessage(Message message);
    void sendNotificationForOffer(Offer offer);
    void sendNotificationForOfferStatusChange(Offer offer);
}
