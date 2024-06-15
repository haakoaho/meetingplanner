package org.toastmasters.meetingplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MeetingplannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingplannerApplication.class, args);
	}

}
