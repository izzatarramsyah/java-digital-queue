package com.example.digital_queue.dto;

import java.time.LocalDate;

public class DailyQueueDTO {

    private LocalDate date;
    private Long totalQueue;
    private String eventType;

    public DailyQueueDTO() {
    }
    
    public DailyQueueDTO(LocalDate date, Long totalQueue, String eventType) {
        this.date = date;
        this.totalQueue = totalQueue;
        this.eventType = eventType;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Long getTotalQueue() {
        return totalQueue;
    }
    public void setTotalQueue(Long totalQueue) {
        this.totalQueue = totalQueue;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    
}
