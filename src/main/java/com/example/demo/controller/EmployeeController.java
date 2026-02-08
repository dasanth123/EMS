package com.example.demo.controller;

import com.example.demo.dtos.requestDto.EmployeeRequestDto;
import com.example.demo.dtos.responseDto.EmployeeResponseDto;
import com.example.demo.enums.RestApiStatusCodes;
import com.example.demo.services.EmployeeService;
import com.example.demo.utils.EndpointBundle;
import com.example.demo.utils.ResponseWrapper;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.EMPLOYEE)
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(EndpointBundle.EMPLOYEE_CREATE)
    public ResponseEntity<ResponseWrapper<EmployeeRequestDto>> toSaveEmployee(@RequestBody EmployeeRequestDto dto){
        EmployeeRequestDto responseDto=  employeeService.toSave(dto);
        return  ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper<>(
                        RestApiStatusCodes.SUCCESS.getCode(),
                        RestApiStatusCodes.CREATED.getMessage(),
                        responseDto));
    }




    @PutMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<EmployeeRequestDto>> toUpdateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDto employeeRequestDto){
        EmployeeRequestDto requestDto= employeeService.toUpdate(id,employeeRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiStatusCodes.UPDATED.getCode(),
                RestApiStatusCodes.UPDATED.getMessage(),
                requestDto
        ));
    }

    @GetMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<EmployeeResponseDto>> toGetById(@PathVariable Long id){
        EmployeeResponseDto responseDto= employeeService.toGetById(id);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiStatusCodes.SUCCESS.getCode(),
                RestApiStatusCodes.RETRIEVED_SUCCESS.getMessage(),
                responseDto
        ));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<EmployeeResponseDto>>> toGetAll(
            @RequestParam(defaultValue="0") Long page,
            @RequestParam(defaultValue = "10") Long size
    ){
       Page<EmployeeResponseDto> responseDto= employeeService.toGetAll(page, size);
       return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
               RestApiStatusCodes.SUCCESS.getCode(),
               RestApiStatusCodes.RETRIEVED_SUCCESS.getMessage(),
               responseDto
       ));
    }

    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<String>> delete(@PathVariable Long id){
        employeeService.delete(id);
       ResponseWrapper<String> response=new ResponseWrapper<>(
               RestApiStatusCodes.DELETED.getCode(),
               RestApiStatusCodes.DELETED.getMessage(),
               null);
       return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
