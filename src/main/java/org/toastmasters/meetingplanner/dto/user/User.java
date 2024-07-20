package org.toastmasters.meetingplanner.dto.user;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.toastmasters.meetingplanner.utility.HashMapConverter;
import org.toastmasters.meetingplanner.utility.ListConverter;

import java.util.*;


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
    @Column
    @Convert(converter = HashMapConverter.class)
    @ColumnTransformer(write = "?::json")
    private Map<String, Integer> roleHistory;

    @Column
    @Convert(converter = ListConverter.class)
    @ColumnTransformer(write = "?::json")
    private List<Object> speechHistory;

    @Column(nullable = false)
    private boolean photoConsent;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;

    public User(){}

    public User(String name, String email, String hashedPassword, String salt, String phoneNumber, boolean photoConsent) {
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.phoneNumber = phoneNumber;
        this.photoConsent = photoConsent;
        speechHistory = new ArrayList<>();
        roleHistory = new HashMap<>();
        roles = new HashSet<>();
        roles.add("USER");
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isPhotoConsent() {
        return photoConsent;
    }


    public Map<String, Integer> getRoleHistory() {
        return roleHistory;
    }

    public void setRoleHistory(Map<String, Integer> roleHistory) {
        this.roleHistory = roleHistory;
    }

    public List<Object> getSpeechHistory() {
        return speechHistory;
    }

    public void setSpeechHistory(List<Object> speechHistory) {
        this.speechHistory = speechHistory;
    }

    public void setPhotoConsent(boolean photoConsent) {
        this.photoConsent = photoConsent;
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
