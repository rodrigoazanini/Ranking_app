package org.ranking_app.dto.request.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AdminRequest {
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 5, max = 25, message = "El username debe tener entre 5 y 25 caracteres")
    private String username;

    @Nullable
    private Boolean admin;

    public AdminRequest() {}
    public AdminRequest(
        String username, 
        Boolean admin
    ) {
        this.username = username;
        this.admin = admin;
    }

    public String getUserName() {
        return username;
    }

    public Boolean getAdmin() {
        return admin;
    }
}
