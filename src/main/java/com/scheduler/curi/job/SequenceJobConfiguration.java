package com.scheduler.curi.job;


import com.scheduler.curi.sequenceMessage.repository.entity.SequenceMessage;
import com.scheduler.curi.sequenceMessage.service.SequenceMessageService;
import com.scheduler.curi.sqs.AmazonSQSSender;
import com.scheduler.curi.sqs.AmazonSQSSenderImpl;
import com.scheduler.curi.sqs.dto.SequenceMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
@Slf4j
public class SequenceJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final AmazonSQSSenderImpl amazonSQSSender;
    private final SequenceMessageService sequenceMessageService;

    @Value("${batch.chunkSize:100}")
    private int chunkSize;

    @Bean
    public JobParametersIncrementer incrementer() {
        return new RunIdIncrementer();
    }
    @Bean
    public Job sequenceMessageJob() {
        return jobBuilderFactory.get("sequenceMessageJob")
                .start(sequenceMessageStep())
                .build();
    }

    @Bean
    public Step sequenceMessageStep() {
        return stepBuilderFactory.get("sequenceMessageStep")
                .<SequenceMessage, SequenceMessageDto> chunk(chunkSize)
                .reader(sequenceMessageReader())
                .processor(sequenceMessageProcessor())
                .writer(sequenceMessageWriter())
                .build();
    }

    @Bean
    public ItemReader<SequenceMessage> sequenceMessageReader(@Value("#{jobParameters['startTime']}") Date startTime) {
        return new IteratorItemReader<>(sequenceMessageService.getAllMessageBeforeNow(LocalDateTime.now()));
    }

    @Bean
    public ItemProcessor<SequenceMessage, SequenceMessageDto> sequenceMessageProcessor() {
        return sequenceMessage -> {
            if (sequenceMessage.getApplyDate().isBefore(LocalDateTime.now())) {
                SequenceMessageDto dto = new SequenceMessageDto();
                dto.setId(sequenceMessage.getId());
                return dto;
            }
            return null;
        };
    }

    @Bean
    public ItemWriter<SequenceMessageDto> sequenceMessageWriter() {
        return items -> items.stream()
                .filter(Objects::nonNull)
                .forEach(item -> {
                    try {
                        amazonSQSSender.sendMessage(item);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                });
    }
}
