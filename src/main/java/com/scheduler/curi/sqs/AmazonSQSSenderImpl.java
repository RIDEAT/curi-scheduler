package com.scheduler.curi.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scheduler.curi.sqs.dto.SequenceMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;


@RequiredArgsConstructor
@Component
public class AmazonSQSSenderImpl implements AmazonSQSSender {
    @Value("${cloud.aws.sqs.queue.url}")
    private String url;

    private final ObjectMapper objectMapper;
    private final AmazonSQS amazonSQS;

    @Override
    public SendMessageResult sendMessage(SequenceMessageDto msg) throws JsonProcessingException {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(url,
                objectMapper.writeValueAsString(msg))
                .withMessageGroupId("curi-sequence-message")
                .withMessageDeduplicationId(UUID.randomUUID().toString());
        return amazonSQS.sendMessage(sendMessageRequest);
    }
}
