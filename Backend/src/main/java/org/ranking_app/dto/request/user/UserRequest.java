package org.ranking_app.dto.request.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 5, max = 10, message = "El username debe tener entre 5 y 10 caracteres")
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @Nullable
    private Long dni;

    public UserRequest() {}
    public UserRequest(
        String username, 
        String email, 
        String password, 
        Long dni
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.dni = dni;
    }

    public String getUserName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getDni() {
        return dni;
    }
}
