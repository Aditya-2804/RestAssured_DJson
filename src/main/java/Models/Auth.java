package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Auth {

//    {
//  "id": 1,
//  "username": "emilys",
//  "email": "emily.johnson@x.dummyjson.com",
//  "firstName": "Emily",
//  "lastName": "Johnson",
//  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", // JWT accessToken (for backward compatibility) in response and cookies
//  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." // refreshToken in response and cookies
//}

    private int id;
    private String username;
    private String password;
    private int expiresInMins;
    private String email;
    private String firstName;
    private String lastName;
    private String accessToken;
    private String refreshToken;

    public Auth(int id, String username, String email, String firstName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
    }

    public Auth() {
    }

    public Auth(String username, String password, int expiresInMins) {
        this.username = username;
        this.password = password;
        this.expiresInMins = expiresInMins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getExpiresInMins() {
        return expiresInMins;
    }

    public void setExpiresInMins(int expiresInMins) {
        this.expiresInMins = expiresInMins;
    }
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", expiresInMins=" + expiresInMins +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
