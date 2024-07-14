package org.toastmasters.meetingplanner.dto.user;

import java.util.List;
import java.util.Map;

public record UserResponse(String name, String email, String phoneNumber, Long id, List<Object> speechHistory,
                           Map<String,Integer> roleHistory, boolean photoConsent) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getName(), user.getEmail(), user.getPhoneNumber(), user.getId(), user.getSpeechHistory(), user.getRoleHistory(), user.isPhotoConsent());
    }
}
