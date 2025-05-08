package com.userservice.security.service.serviceimpl;

import com.userservice.entity.Users;
import com.userservice.exception.EmailNotFoundException;
import com.userservice.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpls implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users= userRepo.findByEmail(username);
        if(users.isPresent()){
            return users.get();
        }else {
            throw new EmailNotFoundException("User","email",username);
        }
    }

}
