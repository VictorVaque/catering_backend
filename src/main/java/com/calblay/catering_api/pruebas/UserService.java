package com.calblay.catering_api.pruebas;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import com.calblay.catering_api.pruebas.UserEntity;
import com.calblay.catering_api.pruebas.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity createUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    public UserEntity readUser(Long id){
        return userRepository.findById(id).orElseThrow();
    }
}
