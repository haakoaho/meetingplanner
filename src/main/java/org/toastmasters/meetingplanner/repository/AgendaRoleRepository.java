package org.toastmasters.meetingplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastmasters.meetingplanner.dto.agenda.AgendaRole;

public interface AgendaRoleRepository extends JpaRepository<AgendaRole, Long> {
}
