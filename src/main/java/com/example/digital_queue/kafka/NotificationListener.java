package com.example.digital_queue.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.digital_queue.dto.QueueEventDTO;

@Component
public class NotificationListener {
    
    @KafkaListener(topics = "queue.event", groupId = "notification-group")
    public void handleQueueEvent(QueueEventDTO event) {
        switch (event.getEventType()) {
            case "QUEUE_JOINED":
                System.out.println("[NOTIF] " + event.getUserFullName() + " joined queue #" + event.getQueueNumber());
                break;
            case "QUEUE_CALLING":
                System.out.println("[NOTIF] Now calling: " + event.getUserFullName() + " (Queue #" + event.getQueueNumber() + ")");
                break;
            case "QUEUE_CALLED":
                System.out.println("[NOTIF] User : " + event.getUserFullName() + " (Queue #" + event.getQueueNumber() + ") has been procssed");
                break;
            default:
                System.out.println("[NOTIF] Unknown event: " + event.getEventType());
        }
    }

}
