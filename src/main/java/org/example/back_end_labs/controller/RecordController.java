package org.example.back_end_labs.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.back_end_labs.model.Record;
import org.example.back_end_labs.service.RecordService;
import org.example.back_end_labs.service.UserService;
import org.example.back_end_labs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<?> createRecord(
            @RequestParam @NotNull(message = "User ID cannot be null") Long userId,
            @RequestParam @NotNull(message = "Category ID cannot be null") Long categoryId,
            @RequestParam @NotNull(message = "Costs cannot be null")
            @Positive(message = "Costs must be a positive value") Double costs) {
        try {
            Record createdRecord = recordService.createRecord(userId, categoryId, costs);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRecord);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<?> getRecordById(@PathVariable Long recordId) {
        try {
            Record record = recordService.getRecordById(recordId);
            return ResponseEntity.ok(record);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long recordId) {
        try {
            recordService.deleteRecordById(recordId);
            return ResponseEntity.ok("Record with ID " + recordId + " deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getRecordsByUserAndCategory(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long categoryId) {
        try {
            List<Record> records = recordService.getFilteredRecords(userId, categoryId);
            return ResponseEntity.ok(records);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}