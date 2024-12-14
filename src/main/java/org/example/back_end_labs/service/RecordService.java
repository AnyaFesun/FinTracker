package org.example.back_end_labs.service;

import org.example.back_end_labs.model.Record;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RecordService {
    private final Map<Long, Record> records = new ConcurrentHashMap<>();
    private final AtomicLong categoryIdCounter = new AtomicLong();

    public Record addRecord(Long userId, Long categoryId, Double costs) {
        Long id = categoryIdCounter.incrementAndGet();
        Record record = new Record(id, userId, categoryId, LocalDateTime.now(), costs);
        records.put(id, record);
        return record;
    }

    public Record getRecordById(Long id) {
        return records.get(id);
    }

    public boolean deleteRecord(Long id) {
        return records.remove(id) != null;
    }

    public List<Record> getRecordsByUserAndCategory(Long userId, Long categoryId) {
        return records.values().stream()
                .filter(record -> (userId == null || record.getUserId().equals(userId)) &&
                        (categoryId == null || record.getCategoryId().equals(categoryId)))
                .collect(Collectors.toList());
    }
}
