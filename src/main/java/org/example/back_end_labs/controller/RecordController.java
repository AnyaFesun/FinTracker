package org.example.back_end_labs.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.back_end_labs.model.Category;
import org.example.back_end_labs.model.Record;
import org.example.back_end_labs.model.User;
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
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public RecordController(RecordService recordService, UserService userService, CategoryService categoryService) {
        this.recordService = recordService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> createRecord(
            @RequestParam @NotNull(message = "User ID cannot be null") Long userId,
            @RequestParam @NotNull(message = "Category ID cannot be null") Long categoryId,
            @RequestParam @NotNull(message = "Costs cannot be null")
            @Positive(message = "Costs must be a positive value") Double costs) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with ID " + userId + " does not exist.");
        }
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Category with ID " + categoryId + " does not exist.");
        }

        Record record = new Record(user.get(), category.get(), costs);
        Record createdRecord = recordService.createRecord(record);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecord);
    }


    @GetMapping("/{recordId}")
    public ResponseEntity<?> getRecordById(@PathVariable Long recordId) {
        Optional<Record> record = recordService.getRecordById(recordId);
        if (record.isPresent()) {
            return ResponseEntity.ok(record.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record with ID " + recordId + " not found.");
        }
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long recordId) {
        Optional<Record> record = recordService.getRecordById(recordId);
        if (record.isPresent()) {
            recordService.deleteRecord(recordId);
            return ResponseEntity.ok("Record with ID " + recordId + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record with ID " + recordId + " not found.\nRecord cannot be deleted.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getRecordsByUserAndCategory(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long categoryId) {
        if (userId == null && categoryId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You must provide at least one of the following parameters: userId or categoryId.");
        }
        List<Record> records = recordService.getRecordsByUserAndCategory(userId, categoryId);
        if (records.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No records found for the given criteria.");
        }
        return ResponseEntity.ok(records);
    }
}