package org.toastmasters.meetingplanner.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.toastmasters.meetingplanner.dto.Deployments;

@Service
public class GitHubService {

    private static final String GITHUB_URL = "https://haakoaho.github.io/speak-fun/deployments.json";

    public Deployments getDeployments() {
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(GITHUB_URL, String.class);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, Deployments.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new Deployments("http://localhost:3000", "http://localhost:8081");
        }
    }
}
