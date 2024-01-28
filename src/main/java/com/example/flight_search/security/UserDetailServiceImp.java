package com.example.flight_search.security;

import com.example.flight_search.entity.User;
import com.example.flight_search.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    private final UserRepo userRepo;
    @Autowired
    public UserDetailServiceImp(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(userName);
        return JwtUserDetails.create(user);
    }
    public UserDetails loadUserById(Long id){
        User user = userRepo.findById(id).get();
        return JwtUserDetails.create(user);
    }
}
