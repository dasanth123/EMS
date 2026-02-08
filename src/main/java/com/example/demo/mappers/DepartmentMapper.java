package com.example.demo.mappers;

import com.example.demo.dtos.requestDto.DepartmentRequestDto;
import com.example.demo.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {


    DepartmentRequestDto toDto(Department department);
    Department toEntity(DepartmentRequestDto dto);

}
