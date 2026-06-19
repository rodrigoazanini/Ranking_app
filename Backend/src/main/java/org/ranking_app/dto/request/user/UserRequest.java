package org.ranking_app.dto.request.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 5, max = 25, message = "El username debe tener entre 5 y 25 caracteres")
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ingresar un email valido")
    private String email;

    @NotBlank
    @Size(min = 8, max = 25, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @Nullable
    private Boolean admin;

    public UserRequest() {}
    public UserRequest(
        String username, 
        String email, 
        String password,
        Boolean admin
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = admin;
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

    public Boolean getAdmin() {
        return admin;
    }
}
