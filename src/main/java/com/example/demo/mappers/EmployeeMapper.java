package com.example.demo.mappers;

import com.example.demo.dtos.requestDto.EmployeeRequestDto;
import com.example.demo.dtos.responseDto.EmployeeResponseDto;
import com.example.demo.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEntity(EmployeeRequestDto employeeRequestDto);
    EmployeeRequestDto toDto(Employee employee);
    EmployeeResponseDto toResponseDto(Employee employee);
}
