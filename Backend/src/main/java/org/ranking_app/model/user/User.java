package org.ranking_app.model.user;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.ranking_app.dto.request.user.UserRequest;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 5, max = 10)
    private String username;

    @Column(nullable = false)
    @NotBlank
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Column(nullable = true)
    @Nullable
    private Long dni;

    public User() {}

    public User(
        Long id,
        String username, 
        String email, 
        String password, 
        Long dni 
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dni = dni;
    }

    static public User fromRequest(UserRequest request) {
        return new User(
            null,
            request.getUserName(),
            request.getEmail(),
            request.getPassword(),
            request.getDni()
        );
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }
    public void setUserName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDni() {
        return dni;
    }
    public void setDni(Long dni) {
        this.dni = dni;
    }
}
