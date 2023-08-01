package com.scheduler.curi.sequenceMessage.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class SequenceMessageRequest {
    @NotNull
    private Long id;

    @NotNull
    private LocalDateTime applyDate;
}
