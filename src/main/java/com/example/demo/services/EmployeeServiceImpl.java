package com.example.demo.services;

import com.example.demo.dtos.requestDto.EmployeeRequestDto;
import com.example.demo.dtos.responseDto.EmployeeResponseDto;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.exceptionHandler.AlreadyExistException;
import com.example.demo.exceptionHandler.ResourceNotFoundException;
import com.example.demo.mappers.EmployeeMapper;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.utils.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeRequestDto toSave(EmployeeRequestDto requestDto){
        boolean exit=employeeRepository.existsByEmail(requestDto.getEmail());
        if(exit){
            throw  new AlreadyExistException(ValidationMessage.ALREADY_EXISTS);
        }
        Employee employee=employeeMapper.toEntity(requestDto);
        Employee res=employeeRepository.save(employee);
        return  employeeMapper.toDto(res);
    }




    @Override
    public EmployeeRequestDto toUpdate(Long id,EmployeeRequestDto dto){
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee Not Found"+id));


        if(employee.getEmail().equals(dto.getEmail())){
            employee.setEmail(dto.getEmail());
        }else{
            boolean exist=employeeRepository.existsByEmail(dto.getEmail());
            if(exist){
                throw  new AlreadyExistException("email already exists"+ ": "+dto.getEmail());
            }

        }



        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setSalary(dto.getSalary());
        Employee employee1=employeeRepository.save(employee);
        return  employeeMapper.toDto(employee1);
    }

    @Override
    public EmployeeResponseDto toGetById(Long id){
        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not Found Employee "+id));
        return employeeMapper.toResponseDto(employee);
    }
    @Override
    public Page<EmployeeResponseDto> toGetAll(Long page, Long size){
        Pageable pageable=PageRequest.of(page.intValue(), size.intValue());
        Page<Employee> res=employeeRepository.findAll(pageable);
        return res.map(employeeMapper::toResponseDto);

    }
    @Override
    public  void delete(Long id){

        Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department Not Found id :"+id));
        employeeRepository.deleteById(id);
    }



}
