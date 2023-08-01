package com.scheduler.curi.sequenceMessage.repository.entity;


import com.scheduler.curi.common.entity.BaseEntity;
import com.scheduler.curi.sequenceMessage.controller.dto.SequenceMessageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SequenceMessage extends BaseEntity {
    @Id
    private Long id;

    private LocalDateTime applyDate;

    @Builder.Default
    private Boolean isSent = false;

    public static SequenceMessage of(SequenceMessageRequest request){
        return SequenceMessage.builder()
                .id(request.getId())
                .applyDate(request.getApplyDate())
                .build();
    }

    public void SetIsSent(Boolean isSent){
        this.isSent = isSent;
    }
}
