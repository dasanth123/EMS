package com.example.demo.dtos.responseDto;

import com.example.demo.utils.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto  {
    private Long id;
    private  String name;
    private String email;
    private String phoneNumber;
    private  String department;
    private  double salary;
    private  String createdAt;
    private  String updatedAt;
}
