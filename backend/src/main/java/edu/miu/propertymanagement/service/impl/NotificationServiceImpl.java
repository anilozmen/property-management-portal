package edu.miu.propertymanagement.service.impl;

import edu.miu.propertymanagement.entity.Message;
import edu.miu.propertymanagement.entity.Offer;
import edu.miu.propertymanagement.entity.OfferStatus;
import edu.miu.propertymanagement.entity.Property;
import edu.miu.propertymanagement.service.EmailService;
import edu.miu.propertymanagement.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final EmailService emailService;

    @Override
    public void sendNotificationForMessage(Message message) {
        if (message.getReply() == null || message.getReply().isEmpty())
            sendMessageNotificationToOwner(message);
        else
            sendMessageNotificationToCustomer(message);
    }

    private void sendMessageNotificationToCustomer(Message message) {
        String email = message.getSender().getEmail();
        String subject = "Response on your messages";
        String body = "Owner responded: <b>" + message.getReply() + "</b>";
        body += getPropertyAnchorTag(message.getProperty());

        emailService.sendWithHTMLBody(email, subject, body);
    }

    private void sendMessageNotificationToOwner(Message message) {
        String email = message.getReceiver().getEmail();
        String subject = "New message on your property listing";
        String body = "Somebody said: <p><b>" + message.getMessage() + "</b></p>";
        body += getPropertyAnchorTag(message.getProperty());

        emailService.sendWithHTMLBody(email, subject, body);
    }

    private static String getPropertyAnchorTag(Property property) {
        return "<a href=\"http://localhost:3000/properties/" + property.getId() + "\">Click here to view in app</a>";
    }

    @Override
    public void sendNotificationForOffer(Offer offer) {
        String email = offer.getProperty().getOwner().getEmail();
        String subject = "New offer on property";
        StringBuilder sb = new StringBuilder();
        sb.append("Somebody just posted an exciting offer to your property. ");

        sb.append("<p><b>Amount: </b>").append(offer.getAmount()).append("</p>");
        sb.append("<p><b>Message: </b>").append(offer.getMessage()).append("</p>");
        sb.append(getPropertyAnchorTag(offer.getProperty()));

        emailService.sendWithHTMLBody(email, subject, sb.toString());
    }

    @Override
    public void sendNotificationForOfferStatusChange(Offer offer) {
        if (offer.getStatus() == OfferStatus.CANCELLED) return;

        String action = "";
        if (offer.getStatus() == OfferStatus.APPROVED)
            action = "approved";
        else if (offer.getStatus() == OfferStatus.REJECTED)
            action = "rejected";

        String email = offer.getCustomer().getEmail();
        String subject = "Offer " + action;

        StringBuilder sb = new StringBuilder();
        sb.append("Your offer was ").append(action).append(" by the owner. ");
        sb.append(getPropertyAnchorTag(offer.getProperty()));

        emailService.sendWithHTMLBody(email, subject, sb.toString());
    }
}
