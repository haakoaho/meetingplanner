package org.toastmasters.meetingplanner.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.toastmasters.meetingplanner.dto.Deployment;

@Service
public class GitHubService {

    private static final String MOBILE_SPEAK_DEPLOYMENT = "https://haakoaho.github.io/speak-fun/deployments/mobile-speak.json";

    public Deployment getFrontendDeployment() {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(MOBILE_SPEAK_DEPLOYMENT, String.class);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, Deployment.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new Deployment("localhost:3000");
        }
    }
}
