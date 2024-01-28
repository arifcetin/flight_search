package com.example.flight_search.service;

import com.example.flight_search.dto.UserDto;
import com.example.flight_search.entity.User;
import com.example.flight_search.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    @Autowired
    public UserService(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    public User getOneUserByUserName(String userName) {
        return userRepo.getOneUserByUserName(userName);
    }

    public UserDto createUser(User newUser) {
        User user = userRepo.save(newUser);
        return modelMapper.map(user, UserDto.class);
    }
}
