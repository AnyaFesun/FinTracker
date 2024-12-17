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
    public Record createRecord(@RequestBody Record record) {
        return recordService.createRecord(record);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<Record> getRecordById(@PathVariable Long recordId) {
        return recordService.getRecordById(recordId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecord(recordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<Record>> getRecordsByUserAndCategory(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long categoryId) {
        List<Record> records = recordService.getRecordsByUserAndCategory(userId, categoryId);
        return ResponseEntity.ok(records);
    }
//    @GetMapping
//    public List<Record> getRecords(@RequestParam(required = false) Long userId,
//                                   @RequestParam(required = false) Long categoryId) {
//        if (userId == null && categoryId == null) {
//            throw new IllegalArgumentException("User ID or Category ID is required");
//        }
//        return recordService.getRecordsByUserAndCategory(userId, categoryId);
//    }
}