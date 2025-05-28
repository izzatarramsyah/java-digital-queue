package com.example.digital_queue.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class QueueEventDTO {
    
    private String eventType; // e.g., QUEUE_JOINED, QUEUE_CALLED
    private UUID userId;
    private String userFullName;
    private Long queueId;
    private Integer queueNumber;
    private LocalDateTime eventTime;
    
    public QueueEventDTO(){

    }
    
    public QueueEventDTO(String eventType, UUID userId, String userFullName, Long queueId, Integer queueNumber,
            LocalDateTime eventTime) {
        this.eventType = eventType;
        this.userId = userId;
        this.userFullName = userFullName;
        this.queueId = queueId;
        this.queueNumber = queueNumber;
        this.eventTime = eventTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public Integer getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(Integer queueNumber) {
        this.queueNumber = queueNumber;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    
}
