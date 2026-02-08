package com.example.demo.controller;

import com.example.demo.dtos.requestDto.UserRequestDto;
import com.example.demo.entity.User;
import com.example.demo.enums.RestApiStatusCodes;
import com.example.demo.services.UserService;
import com.example.demo.utils.EndpointBundle;
import com.example.demo.utils.ResponseWrapper;
import com.example.demo.utils.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointBundle.USER)
@AllArgsConstructor
@NoArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<UserRequestDto>> toSaveUser(@RequestBody UserRequestDto dto){

     UserRequestDto userRequestDto = userService.toSave(dto);
     return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
             RestApiStatusCodes.SUCCESS.getCode() ,
             ValidationMessage.LOGIN,
             userRequestDto
             ));
    }
}
