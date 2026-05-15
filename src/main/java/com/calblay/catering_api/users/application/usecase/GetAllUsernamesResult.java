package com.calblay.catering_api.users.application.usecase;

import java.util.List;

public class GetAllUsernamesResult
{
    private List<String> usernames;

    public GetAllUsernamesResult(List<String> usernames)
    {
        this.usernames = usernames;
    }

    public List<String> getUsernames()
    {
        return usernames;
    }
}