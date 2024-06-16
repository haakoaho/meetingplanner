package org.toastmasters.meetingplanner.dto.user;

public record UserResponse(String username, String name, String email, String phoneNumber) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getUsername(), user.getName(), user.getEmail(), user.getPhoneNumber());
    }
}
