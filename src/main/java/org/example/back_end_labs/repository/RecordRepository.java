package org.example.back_end_labs.repository;

import org.example.back_end_labs.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByUser_IdAndCategory_Id(Long userId, Long categoryId);
    List<Record> findByUser_IdOrCategory_Id(Long userId, Long categoryId);
}