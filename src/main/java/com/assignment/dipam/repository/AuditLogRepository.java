package com.assignment.dipam.repository;

import com.assignment.dipam.entity.AuditLogBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLogBO, String> {
}
