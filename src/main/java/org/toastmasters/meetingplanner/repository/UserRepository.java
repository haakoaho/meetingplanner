package org.toastmasters.meetingplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastmasters.meetingplanner.dto.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}