package com.scheduler.curi.sequenceMessage.repository;

import com.scheduler.curi.sequenceMessage.repository.entity.SequenceMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SequenceMessageRepository extends JpaRepository<SequenceMessage, Long> {
    List<SequenceMessage> findAllByIsSentFalseAndApplyDateBefore(LocalDateTime now);
}
