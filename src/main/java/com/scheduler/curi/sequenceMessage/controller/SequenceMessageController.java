package com.scheduler.curi.sequenceMessage.controller;

import com.scheduler.curi.sequenceMessage.controller.dto.SequenceMessageRequest;
import com.scheduler.curi.sequenceMessage.service.SequenceMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sequenceMessage")
public class SequenceMessageController {

    private final SequenceMessageService sequenceMessageService;
    @PostMapping
    public ResponseEntity<Void> createMessage(@RequestBody @Valid SequenceMessageRequest request){
        sequenceMessageService.createMessage(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{sequeneceId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long sequeneceId){
        sequenceMessageService.deleteMessage(sequeneceId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
