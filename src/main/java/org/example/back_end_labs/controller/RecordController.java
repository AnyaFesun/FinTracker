package org.example.back_end_labs.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.back_end_labs.model.Record;
import org.example.back_end_labs.model.User;
import org.example.back_end_labs.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Record> createRecord(@RequestParam Long userId, @RequestParam @NotNull Long categoryId,
            @RequestParam @Positive(message = "Costs must be a positive value") Double costs) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recordService.createRecord(userId, categoryId, costs));
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<Record> getRecordById(@PathVariable Long recordId) {
        return ResponseEntity.ok(recordService.getRecordById(recordId));
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecordById(recordId);
        return ResponseEntity.ok("Record with ID " + recordId + " deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Record>> getRecordsByUserAndCategory(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long categoryId) {
        return ResponseEntity.ok(recordService.getFilteredRecords(userId, categoryId));
    }
}