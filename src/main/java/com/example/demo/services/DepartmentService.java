package com.example.demo.services;

import com.example.demo.dtos.requestDto.DepartmentRequestDto;
import org.springframework.data.domain.Page;

public interface DepartmentService {
    DepartmentRequestDto toSave(DepartmentRequestDto dto);

    DepartmentRequestDto toUpdate(DepartmentRequestDto dto, Long id);

    DepartmentRequestDto toGetById(Long id);

    Page<DepartmentRequestDto> toGetAll(Long page, Long size);

    void Delete(Long id);

}