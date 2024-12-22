package org.example.back_end_labs.service;

import org.example.back_end_labs.model.Account;
import org.example.back_end_labs.model.Category;
import org.example.back_end_labs.model.Record;
import org.example.back_end_labs.model.User;
import org.example.back_end_labs.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RecordService {
    private final UserService userService;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(UserService userService, CategoryService categoryService,
                         RecordRepository recordRepository, AccountService accountService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.recordRepository = recordRepository;
        this.accountService = accountService;
    }

    public Record createRecord(Long userId, Long categoryId, Double costs) {
        User user = userService.getUserById(userId);
        Category category = categoryService.getCategoryById(categoryId);
        Account account = accountService.getAccountByUserId(userId);
        if (!account.canWithdraw(costs)) {
            throw new IllegalArgumentException("Insufficient funds in account!");
        }
        account.withdraw(costs);

        Record record = new Record(user, category, costs);
        return recordRepository.save(record);
    }

    public Record getRecordById(Long recordId, Long userId) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new NoSuchElementException("Record with ID " + recordId + " not found."));
        if (!record.getUser().getId().equals(userId)) {
            throw new NoSuchElementException("Record with ID " + recordId + " not found in your requests!");
        }

        return record;
    }

    public void deleteRecordById(Long recordId, Long userId) {
        Record record = getRecordById(recordId, userId);
        recordRepository.delete(record);
    }

    public List<Record> getFilteredRecords(Long userId, Long categoryId) {
        List<Record> records = getRecordsByUserAndCategory(userId, categoryId);
        if (records.isEmpty()) {
            throw new NoSuchElementException("No records found for the given criteria.");
        }
        return records;
    }

    public List<Record> getRecordsByUserAndCategory(Long userId, Long categoryId) {
        if (userId != null && categoryId != null) {
            return recordRepository.findByUser_IdAndCategory_Id(userId, categoryId);
        } else if (userId != null) {
            return recordRepository.findByUser_IdOrCategory_Id(userId, null);
        } else {
            return recordRepository.findAll();
        }
    }
}
