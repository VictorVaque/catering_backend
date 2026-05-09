package com.calblay.catering_api.usuarios.application.usecase;

import org.springframework.stereotype.Service;
import com.calblay.catering_api.usuarios.domain.model.User;
import com.calblay.catering_api.usuarios.application.port.out.UserRepositoryPort;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllUsernamesUseCase
{
    private final UserRepositoryPort userRepositoryPort;

    public GetAllUsernamesUseCase(UserRepositoryPort userRepositoryPort)
    {
        this.userRepositoryPort = userRepositoryPort;
    }

    public GetAllUsernamesResult getAllUsernames()
    {
        List<User> usuarios = userRepositoryPort.findAll();
        List<String> nicks = usuarios.stream()
                .map(user -> user.getUsuario())
                .collect(Collectors.toList());

        return new GetAllUsernamesResult(nicks);
    }
}