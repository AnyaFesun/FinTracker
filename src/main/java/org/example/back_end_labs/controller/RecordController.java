package org.example.back_end_labs.controller;

import org.example.back_end_labs.model.Record;
import org.example.back_end_labs.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }
    @PostMapping
    public ResponseEntity<Record> createRecord(@RequestParam Long userId, @RequestParam Long categoryId, @RequestParam Double costs) {
        Record record = recordService.addRecord(userId, categoryId, costs);
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }

    @GetMapping("/{recordId}")
    public Optional<Record> getRecord(@PathVariable Long recordId) {
        return Optional.ofNullable(recordService.getRecordById(recordId));
    }

    @DeleteMapping("/{recordId}")
    public void deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecord(recordId);
    }

    @GetMapping
    public List<Record> getRecords(@RequestParam(required = false) Long userId,
                                   @RequestParam(required = false) Long categoryId) {
        if (userId == null && categoryId == null) {
            throw new IllegalArgumentException("User ID or Category ID is required");
        }
        return recordService.getRecordsByUserAndCategory(userId, categoryId);
    }
}