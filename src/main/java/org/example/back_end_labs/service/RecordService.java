package org.example.back_end_labs.service;

import org.example.back_end_labs.model.Record;
import org.example.back_end_labs.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }
    public Record createRecord(Record record) {
        record.setCreationDate(LocalDateTime.now());
        return recordRepository.save(record);
    }

    public Optional<Record> getRecordById(Long id) {
        return recordRepository.findById(id);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    public List<Record> getRecordsByUserAndCategory(Long userId, Long categoryId) {
        if (userId != null && categoryId != null) {
            return recordRepository.findByUser_IdAndCategory_Id(userId, categoryId);
        } else if (userId != null) {
            return recordRepository.findByUser_IdOrCategory_Id(userId, null);
        } else if (categoryId != null) {
            return recordRepository.findByUser_IdOrCategory_Id(null, categoryId);
        } else {
            return recordRepository.findAll();
        }
    }
}
