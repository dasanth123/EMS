package com.example.demo.mappers;

import com.example.demo.dtos.requestDto.UserRequestDto;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRequestDto toDTO(User user);
    User toEntity(UserRequestDto userRequestDto);

}
