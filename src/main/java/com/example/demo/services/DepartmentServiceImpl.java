package com.example.demo.services;

import com.example.demo.dtos.requestDto.DepartmentRequestDto;
import com.example.demo.entity.Department;
import com.example.demo.exceptionHandler.AlreadyExistException;
import com.example.demo.exceptionHandler.ResourceNotFoundException;
import com.example.demo.mappers.DepartmentMapper;
import com.example.demo.repositories.DepartmentRepository;
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

public class DepartmentServiceImpl implements  DepartmentService {
     @Autowired
    private DepartmentRepository departmentRepository;

     @Autowired
    private DepartmentMapper departmentMapper;

     @Override
    public DepartmentRequestDto toSave(DepartmentRequestDto dto){
         boolean exist=departmentRepository.existsByDepartmentName(dto.getDepartmentName());
         if(exist){
             throw  new AlreadyExistException(ValidationMessage.ALREADY_EXISTS);
         }
         Department department=departmentMapper.toEntity(dto);
         Department res=departmentRepository.save(department);
          return  departmentMapper.toDto(res);

     }
     @Override
    public  DepartmentRequestDto toUpdate(DepartmentRequestDto dto,Long id){
         Department department=departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Department Not Found id:"+id));
         if (department.getDepartmentName().equals(dto.getDepartmentName())){
             department.setDepartmentName(dto.getDepartmentName());
         } else{
             boolean exist= departmentRepository.existsByDepartmentName(dto.getDepartmentName());

             if(exist){
                 throw  new AlreadyExistException(dto.getDepartmentName()+" department already exists");
             }
         }

         department.setDepartmentName(dto.getDepartmentName());
         department.setDescription(dto.getDescription());
         Department department1=departmentRepository.save(department);
         return departmentMapper.toDto(department1);

     }

     @Override
     public DepartmentRequestDto toGetById(Long id){
         Department department=departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department Not Found  id:"+id));
         return departmentMapper.toDto(department);

     }

     @Override
    public Page<DepartmentRequestDto> toGetAll(Long page, Long size){
         Pageable pageable= PageRequest.of(page.intValue(),size.intValue());
         Page<Department> department=departmentRepository.findAll(pageable);
         return department.map(departmentMapper::toDto);
     }

     @Override
    public  void Delete(Long id){

        Department department=departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department Not Found id :"+id));
        departmentRepository.deleteById(id);
     }

}
