package com.assignment.dipam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "audit_log")
@RequiredArgsConstructor
public class AuditLogBO extends BaseBO{

    private String message;
    private String tableName;
}
