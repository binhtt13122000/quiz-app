package binhtt.dtos;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String email;
    private String password;
    private String name;
    private boolean active;
    private int role;

    public UserDTO(String email, String name, boolean active, int role) {
        this.email = email;
        this.name = name;
        this.active = active;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
