package com.scheduler.curi.scheduler;

import com.scheduler.curi.job.SequenceJobConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SequenceScheduler {
    private final JobLauncher jobLauncher;
    private final SequenceJobConfiguration jobConfiguration;

    @Scheduled(cron = "${spring.sequence.cron}")
    public void runJob() {
        JobParameters parameters = new JobParametersBuilder()
                .addDate("startTime", new Date())
                .toJobParameters();
        try {
            jobLauncher.run(jobConfiguration.sequenceMessageJob(), parameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}
