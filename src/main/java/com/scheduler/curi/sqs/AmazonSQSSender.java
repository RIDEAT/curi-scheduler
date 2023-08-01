package com.scheduler.curi.sqs;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.scheduler.curi.sqs.dto.SequenceMessageDto;

public interface AmazonSQSSender {
    SendMessageResult sendMessage(SequenceMessageDto msg) throws JsonProcessingException;
}
