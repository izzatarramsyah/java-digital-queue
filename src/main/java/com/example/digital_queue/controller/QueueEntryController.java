package com.example.digital_queue.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_queue.dto.DailyQueueDTO;
import com.example.digital_queue.dto.QueueEntryDTO;
import com.example.digital_queue.dto.SuccessResponse;
import com.example.digital_queue.entity.QueueEntry;
import com.example.digital_queue.service.QueueEntryService;

@RestController
@RequestMapping("/api/queue")
public class QueueEntryController {
    
    private final QueueEntryService queueEntryService;

    public QueueEntryController(QueueEntryService queueEntryService) {
        this.queueEntryService = queueEntryService;
    }

    @GetMapping("/today")
    public ResponseEntity<?> getDailyQueueReport() {
        List<DailyQueueDTO> response = queueEntryService.getDailyQueueReport();
        String messege = response == null  ? "Queue is empty" : "success";
        SuccessResponse<List<DailyQueueDTO> > successResponse = new SuccessResponse<>(messege, response);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @PostMapping("/{queueId}/join")
    public ResponseEntity<QueueEntry> joinQueue(
            @PathVariable Long queueId,
            @RequestParam String userId) {
        QueueEntry response = queueEntryService.joinQueue(queueId, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{queueId}/entries")
    public ResponseEntity<?> getAllQueueEntries(@PathVariable Long queueId) {
        List<QueueEntryDTO> entries = queueEntryService.getActiveQueue(queueId);
        String messege = entries == null  ? "No Queue Found for this queue" : "success";
        SuccessResponse<List<QueueEntryDTO>> successResponse = new SuccessResponse<>(messege, entries);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @PostMapping("/{queueId}/next")
    public ResponseEntity<?> callNextInQueue(@PathVariable Long queueId) {
        QueueEntryDTO response = queueEntryService.callNext(queueId);
        String messege = response == null  ? "Queue is empty" : "success";
        SuccessResponse<QueueEntryDTO> successResponse = new SuccessResponse<>(messege, response);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

}
