package org.example.back_end_labs.model;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class User {
    private Long id;
    private String name;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
