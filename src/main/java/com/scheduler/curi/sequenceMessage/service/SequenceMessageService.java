package com.scheduler.curi.sequenceMessage.service;


import com.scheduler.curi.common.exception.CuriException;
import com.scheduler.curi.common.exception.ErrorType;
import com.scheduler.curi.sequenceMessage.controller.dto.SequenceMessageRequest;
import com.scheduler.curi.sequenceMessage.repository.SequenceMessageRepository;
import com.scheduler.curi.sequenceMessage.repository.entity.SequenceMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SequenceMessageService {
    private final SequenceMessageRepository sequenceMessageRepository;

    public void createMessage(SequenceMessageRequest request) {
        var msg = SequenceMessage.of(request);
        sequenceMessageRepository.save(msg);
    }

    public List<SequenceMessage> getAllMessageBeforeNow(LocalDateTime now) {
        return sequenceMessageRepository.findAllByIsSentFalseAndApplyDateBefore(now);
    }

    @Transactional
    public void deleteMessage(Long id) {
        var msg = sequenceMessageRepository.findById(id)
                .orElseThrow(() -> new CuriException(HttpStatus.NOT_FOUND, ErrorType.INVALID_REQUEST_ERROR));
        msg.SetIsSent(true);
    }
}
