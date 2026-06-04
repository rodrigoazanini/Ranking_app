package org.ranking_app.dto.response.user;

import org.ranking_app.model.user.User;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Long dni;

    public UserResponse() {}

    public UserResponse(
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

    static public UserResponse fromEntity(User user) {
        return new UserResponse(
            user.getId(), 
            user.getUserName(), 
            user.getEmail(),
            user.getPassword(),
            user.getDni()
        );
    }

    public Long getId() {
        return id;
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
