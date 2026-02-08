package com.example.demo.controller;


import com.example.demo.dtos.requestDto.DepartmentRequestDto;
import com.example.demo.entity.Department;
import com.example.demo.enums.RestApiStatusCodes;
import com.example.demo.services.DepartmentService;
import com.example.demo.utils.EndpointBundle;
import com.example.demo.utils.ResponseWrapper;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(EndpointBundle.DEPARTMENT)
public class DepartmentController {
    @Autowired
    private  DepartmentService departmentService;

    @PostMapping("/added")
    public ResponseEntity<ResponseWrapper<DepartmentRequestDto>> toSave(@RequestBody DepartmentRequestDto dto){
        DepartmentRequestDto departmentRequestDto=departmentService.toSave(dto);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiStatusCodes.SUCCESS.getCode(),
                RestApiStatusCodes.CREATED.getMessage(),
                departmentRequestDto
        ));

    }

    @PutMapping(EndpointBundle.ID)
    public  ResponseEntity<ResponseWrapper<DepartmentRequestDto>> toupdate(@RequestBody DepartmentRequestDto dto,@PathVariable Long id){
        DepartmentRequestDto requestDto=departmentService.toUpdate(dto,id);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiStatusCodes.UPDATED.getCode(),
                RestApiStatusCodes.UPDATED.getMessage(),
                requestDto
        ));
    }

    @GetMapping(EndpointBundle.ID)
    public  ResponseEntity<ResponseWrapper<DepartmentRequestDto>> toGetById(@PathVariable Long id ){
        DepartmentRequestDto response=departmentService.toGetById(id);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiStatusCodes.RETRIEVED_SUCCESS.getCode(),
                RestApiStatusCodes.RETRIEVED_SUCCESS.getMessage(),
                response
        ));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<DepartmentRequestDto>>> toGetAll(
            @RequestParam(defaultValue = "0") Long page,
            @RequestParam(defaultValue = "10") Long size){
        Page<DepartmentRequestDto> departmentRequestDto=departmentService.toGetAll(page,size);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
                RestApiStatusCodes.RETRIEVED_SUCCESS.getCode(),
                RestApiStatusCodes.RETRIEVED_SUCCESS.getMessage(),
                departmentRequestDto
        ));
    }
    @DeleteMapping(EndpointBundle.ID)
    public ResponseEntity<ResponseWrapper<String>> delete(@PathVariable Long id){
        departmentService.Delete(id);
        ResponseWrapper<String> response=new ResponseWrapper<>(
                RestApiStatusCodes.DELETED.getCode(),
                "Department Deleted Successfully",
                null
        );
      return  ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
