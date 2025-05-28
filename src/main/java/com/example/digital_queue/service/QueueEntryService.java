package com.example.digital_queue.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.digital_queue.dto.DailyQueueDTO;
import com.example.digital_queue.dto.QueueEntryDTO;
import com.example.digital_queue.dto.QueueEventDTO;
import com.example.digital_queue.entity.Queue;
import com.example.digital_queue.entity.QueueEntry;
import com.example.digital_queue.entity.User;
import com.example.digital_queue.exception.CustomException;
import com.example.digital_queue.kafka.KafkaService;
import com.example.digital_queue.kafka.QueueEventProducer;
import com.example.digital_queue.repository.QueueEntryRepository;
import com.example.digital_queue.repository.QueueRepository;
import com.example.digital_queue.repository.UserRepository;

@Service
public class QueueEntryService {
    
    private final QueueRepository queueRepository;
    private final QueueEntryRepository queueEntryRepository;
    private final UserRepository userRepository;
    private QueueEventProducer eventProducer;

    public QueueEntryService(QueueRepository queueRepository, 
            QueueEntryRepository queueEntryRepository,
            UserRepository userRepository,
            QueueEventProducer eventProducer) {
        this.queueRepository = queueRepository;
        this.queueEntryRepository = queueEntryRepository;
        this.userRepository = userRepository;
        this.eventProducer = eventProducer;
    }

     public QueueEntry joinQueue(Long serviceId, String userIdAsString) {
        Queue queue = queueRepository.findById(serviceId)
                .orElseThrow(() ->  new CustomException("QUEUE_FAILED", "Queue Not Found", HttpStatus.NOT_FOUND));

        int nextQueueNumber = queueEntryRepository.findTopByQueueOrderByQueueNumberDesc(queue)
                .map(e -> e.getQueueNumber() + 1)
                .orElse(1);
                
        UUID userUuid = UUID.fromString(userIdAsString);
        User user = userRepository.findById(userUuid)
            .orElseThrow(() -> new CustomException("USER_NOT_FOUND", "User Not Found", HttpStatus.NOT_FOUND));

        QueueEntry entry = QueueEntry.builder()
                .queue(queue)
                .user(user)
                .queueNumber(nextQueueNumber)
                .joinedAt(LocalDateTime.now())
                .called(false)
                .build();

        eventProducer.SendQueueEvent(new QueueEventDTO(
                "QUEUE_JOINED",
                user.getId(),
                user.getFullname(),
                queue.getId(),
                nextQueueNumber,
                LocalDateTime.now()
        ));
        return queueEntryRepository.save(entry);
    }

    public List<QueueEntryDTO> getActiveQueue(Long serviceId) {
        Queue queue = queueRepository.findById(serviceId)
                .orElseThrow(() ->  new CustomException("QUEUE_FAILED", "Queue Not Found", HttpStatus.NOT_FOUND));

        List<QueueEntry> queueEntries =  queueEntryRepository.findByQueueAndCalledFalseOrderByQueueNumberAsc(queue);
    
        if (queueEntries.isEmpty()) {
            return null;
        }

        return queueEntries.stream()
            .map(entry -> new QueueEntryDTO(
                    entry.getId(),
                    entry.getQueueNumber(),
                    entry.getJoinedAt(),
                    entry.isCalled(),
                    entry.getUser().getId(),
                    entry.getUser().getFullname()
            ))
            .collect(Collectors.toList());
    }

    public QueueEntryDTO callNext(Long serviceId) {
        Queue queue = queueRepository.findById(serviceId)
                .orElseThrow(() ->  new CustomException("QUEUE_FAILED", "Queue Not Found", HttpStatus.NOT_FOUND));

        List<QueueEntry> activeQueue =  queueEntryRepository.findByQueueIdAndCalledFalseOrderByQueueNumberAsc(queue.getId());

        if (activeQueue.isEmpty()) {
            return null;
        }

        QueueEntry next = activeQueue.get(0);
        next.setCalled(true);
        QueueEntry saved = queueEntryRepository.save(next);

        eventProducer.SendQueueEvent(new QueueEventDTO(
                "QUEUE_CALLED",
                saved.getUser().getId(),
                saved.getUser().getFullname(),
                queue.getId(),
                saved.getQueueNumber(),
                LocalDateTime.now()
        ));

        return new QueueEntryDTO(
            saved.getId(),
            saved.getQueueNumber(),
            saved.getJoinedAt(),
            saved.isCalled(),
            saved.getUser().getId(),
            saved.getUser().getFullname()
        );
    }

    public List<DailyQueueDTO> getDailyQueueReport() {
        return queueEntryRepository.getDailyQueueCountNative()
            .stream()
            .map(row -> new DailyQueueDTO(
                ((Date) row[0]).toLocalDate(),             // Convert date to LocalDate
                ((Number) row[1]).longValue(),            // Convert total_queue to long
                (String) row[2]                           // description
            ))
            .collect(Collectors.toList());
    }
    

}
