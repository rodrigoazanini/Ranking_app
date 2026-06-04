package org.ranking_app.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 5, max = 50, message = "El nombre debe tener entre 5 y 10 caracteres")
    private String name;

    public CategoryRequest() {}
    public CategoryRequest(
        String name
    ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
