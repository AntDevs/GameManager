package com.gamemanager.services;

import com.gamemanager.beens.Answer;
import com.gamemanager.beens.User;
import com.gamemanager.beens.UserGame;

import java.util.List;

public interface UserService {

    public User getUser(String userName) throws Exception;
    public List<User> getUsers() throws Exception;
    public Integer addGameAnswer(Answer answer, String userName) throws Exception;
    public Integer getUserEarnedPoints(String userName) throws Exception;
}
