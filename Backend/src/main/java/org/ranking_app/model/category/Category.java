package org.ranking_app.model.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.ranking_app.dto.request.category.CategoryRequest;

@Entity
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 5, max = 50)
    private String name;

    public Category() {}

    public Category(
        Long id,
        String name 
    ) {
        this.id = id;
        this.name = name;
    }

    static public Category fromRequest(CategoryRequest request) {
        return new Category(
            null,
            request.getName()
        );
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
