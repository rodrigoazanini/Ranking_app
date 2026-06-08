package org.ranking_app.dto.response.user;

import org.ranking_app.model.user.User;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String password;

    public UserResponse() {}

    public UserResponse(
        Long id,
        String username, 
        String email, 
        String password
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    static public UserResponse fromEntity(User user) {
        return new UserResponse(
            user.getId(), 
            user.getUserName(), 
            user.getEmail(),
            user.getPassword()
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
}
