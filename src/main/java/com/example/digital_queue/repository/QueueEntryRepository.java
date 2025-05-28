package com.example.digital_queue.repository;

import com.example.digital_queue.entity.QueueEntry;
import com.example.digital_queue.dto.DailyQueueDTO;
import com.example.digital_queue.entity.Queue;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QueueEntryRepository extends JpaRepository<QueueEntry, Long> {

    List<QueueEntry> findByQueueIdAndCalledFalseOrderByQueueNumberAsc(Long queueId);

    List<QueueEntry> findByQueueAndCalledFalseOrderByQueueNumberAsc(Queue queue);

    Optional<QueueEntry> findTopByQueueOrderByQueueNumberDesc(Queue queue);

    @Query(value = "SELECT DATE(a.joined_at) AS date, COUNT(*) AS total_queue, b.description " +
        "FROM queue_entry a " +
        "JOIN queue b ON a.queue_id = b.id " +
        "GROUP BY DATE(a.joined_at), b.description", nativeQuery = true)
    List<Object[]> getDailyQueueCountNative();

}
