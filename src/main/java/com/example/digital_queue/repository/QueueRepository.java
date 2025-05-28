package com.example.digital_queue.repository;

import com.example.digital_queue.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueRepository extends JpaRepository<Queue, Long> {
    
}
