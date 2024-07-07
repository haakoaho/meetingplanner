package org.toastmasters.meetingplanner.dto.user;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.toastmasters.meetingplanner.utility.HashMapConverter;

import java.util.Map;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    @Convert(converter = HashMapConverter.class)
    @ColumnTransformer(write = "?::json")
    private Map<String, Object> meetingHistory;

    public User(){}

    public User(String name, String email, String hashedPassword, String salt, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.phoneNumber = phoneNumber;
    }

    public Map<String, Object> getMeetingHistory() {
        return meetingHistory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
