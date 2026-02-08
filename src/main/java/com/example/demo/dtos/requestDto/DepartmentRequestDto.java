package com.example.demo.dtos.requestDto;

import com.example.demo.utils.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequestDto  {
    private Long id;
    private String departmentName;
    private  String description;
    private Instant createdAt;
    private Instant updatedAt;
}
