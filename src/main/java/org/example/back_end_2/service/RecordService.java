package org.example.back_end_2.service;

import org.example.back_end_2.model.Record;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private List<Record> records = new ArrayList<>();

    public List<Record> getAllRecords() {
        return records;
    }

    public Optional<Record> getRecordById(Long recordId) {
        return records.stream().filter(record -> record.getId().equals(recordId)).findFirst();
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public void deleteRecord(Long recordId) {
        records.removeIf(record -> record.getId().equals(recordId));
    }

    public List<Record> getRecordsByUserAndCategory(Long userId, Long categoryId) {
        return records.stream()
                .filter(record -> (userId == null || record.getUserId().equals(userId)) &&
                        (categoryId == null || record.getCategoryId().equals(categoryId)))
                .collect(Collectors.toList());
    }
}
