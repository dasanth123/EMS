package com.example.demo.services;

import com.example.demo.dtos.requestDto.EmployeeRequestDto;
import com.example.demo.dtos.responseDto.EmployeeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    EmployeeRequestDto toSave(EmployeeRequestDto employeeRequestDto);

    EmployeeRequestDto toUpdate(Long id,EmployeeRequestDto employeeRequestDto);

    EmployeeResponseDto toGetById(Long id);

    Page<EmployeeResponseDto> toGetAll(Long page, Long size);

    void delete(Long id);
}
