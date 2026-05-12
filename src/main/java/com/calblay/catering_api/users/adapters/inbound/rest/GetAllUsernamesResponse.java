package com.calblay.catering_api.users.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor

public class GetAllUsernamesResponse
{
    private List<String> usuarios;
}