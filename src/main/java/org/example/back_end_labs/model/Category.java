package org.example.back_end_labs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name cannot be empty")
    @Column(nullable = false, unique = true)
    private String name;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
