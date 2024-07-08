package org.toastmasters.meetingplanner.dto.user;

import java.util.Map;

public record UserResponse(String name, String email, String phoneNumber, Long id, Map<String, Object> meetingHistory,
                           boolean photoConsent) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getName(), user.getEmail(), user.getPhoneNumber(), user.getId(), user.getMeetingHistory(), user.isPhotoConsent());
    }
}
