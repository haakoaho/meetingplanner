package org.toastmasters.meetingplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastmasters.meetingplanner.dto.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
