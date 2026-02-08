package com.example.demo.dtos.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDto {
    private Long id;
    @NotEmpty
    private  String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private  String department;
    @NotEmpty
    private  double salary;
}
