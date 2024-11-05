package org.example.back_end_2.controller;

import org.example.back_end_2.model.Record;
import org.example.back_end_2.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public void createRecord(@RequestBody Record record) {
        record.setCreationDate(LocalDateTime.now());
        recordService.addRecord(record);
    }

    @GetMapping("/{recordId}")
    public Optional<Record> getRecord(@PathVariable Long recordId) {
        return recordService.getRecordById(recordId);
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