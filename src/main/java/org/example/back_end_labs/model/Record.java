package org.example.back_end_labs.model;

import java.time.LocalDateTime;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class Record {
    private Long id;
    private Long userId;
    private Long categoryId;
    private LocalDateTime creationDate;
    private Double costs;

    public Record(Long id, Long userId, Long categoryId, LocalDateTime creationDate, Double costs) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.creationDate = creationDate;
        this.costs = costs;
    }
}
