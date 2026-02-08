package com.example.demo.services;

import com.example.demo.dtos.requestDto.UserRequestDto;

public interface UserService {
    UserRequestDto toSave(UserRequestDto userRequestDto);
}
