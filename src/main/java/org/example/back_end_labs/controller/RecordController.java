package org.example.back_end_labs.controller;

import org.example.back_end_labs.model.Record;
import org.example.back_end_labs.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/record")
@Validated
public class RecordController {
    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public ResponseEntity<Record> createRecord(@RequestParam Long categoryId,
                                               @RequestParam Double costs, @AuthenticationPrincipal String userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recordService.createRecord(Long.parseLong(userId), categoryId, costs));
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<Record> getRecordById(@PathVariable Long recordId, @AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(recordService.getRecordById(recordId, Long.parseLong(userId)));
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long recordId, @AuthenticationPrincipal String userId) {
        recordService.deleteRecordById(recordId, Long.parseLong(userId));
        return ResponseEntity.ok("Record with ID " + recordId + " deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Record>> getRecordsByUserAndCategory(
            @RequestParam(required = false) Long categoryId,
            @AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(recordService.getFilteredRecords(Long.parseLong(userId), categoryId));
    }
}