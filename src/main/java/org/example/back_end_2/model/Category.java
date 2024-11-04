package org.example.back_end_2.model;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class Category {
    private Long id;
    private String name;
    public  Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
