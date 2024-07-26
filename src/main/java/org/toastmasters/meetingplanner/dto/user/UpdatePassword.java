package org.toastmasters.meetingplanner.dto.user;

public record UpdatePassword(String oldPassword, String newPassword) {
}
