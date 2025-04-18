package com.cnpmnc.roms.service;

import com.cnpmnc.roms.entity.BaseUser;
import com.cnpmnc.roms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public BaseUser loadUserByUsername(String email) throws UsernameNotFoundException {
        BaseUser baseUser = userRepository.findByEmail(email);
        if (baseUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return baseUser;
    }
}
