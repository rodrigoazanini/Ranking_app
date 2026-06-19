package org.ranking_app.dto.response.user;

import org.ranking_app.model.user.User;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Boolean admin;

    public UserResponse() {}

    public UserResponse(
        Long id,
        String username, 
        String email,
        Boolean admin
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.admin = admin;
    }

    static public UserResponse fromEntity(User user) {
        return new UserResponse(
            user.getId(), 
            user.getUserName(), 
            user.getEmail(),
            user.getAdmin()
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

    public Boolean getAdmin() {
        return admin;
    }
}
