package life.henrytech.backend.dto;

public class AuthData {
    private String username;
    private String email;
    private String token;
    
    public AuthData() {
    }
    
    public AuthData(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
}

