package org.toastmasters.meetingplanner.dto.user;

import org.toastmasters.meetingplanner.dto.RecordSpeech;

import java.util.List;
import java.util.Map;

public record UpdateUser(String name, String email, String phoneNumber, boolean photoConsent, Map<String,Integer> roleHistory, List<RecordSpeech> speechHistory) {
}
