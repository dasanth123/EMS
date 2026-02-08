package com.example.demo.services;

import com.example.demo.dtos.requestDto.UserRequestDto;

import com.example.demo.entity.User;
import com.example.demo.exceptionHandler.BusinessRuleViolationException;
import com.example.demo.exceptionHandler.ResourceNotFoundException;
import com.example.demo.exceptionHandler.UnauthorizedException;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor

public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    public UserRequestDto toSave(UserRequestDto userRequestDto){
       User  user= userRepository.findByEmail(userRequestDto.getEmail());



           if(user==null || !user.getPassword().equals(userRequestDto.getPassword())) {
               throw  new UnauthorizedException("Invalid email or password");

           }

           return userMapper.toDTO(user);

    }
}
