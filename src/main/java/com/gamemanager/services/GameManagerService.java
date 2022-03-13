package com.gamemanager.services;

import com.gamemanager.beens.Answer;

import java.util.Hashtable;

public interface GameManagerService {

    public Hashtable<String, Object> AnswerQuestion(Answer question, String userName );

    public Hashtable<String, Object> getLeaderboard(Long gameId);

    public boolean isError(Hashtable<String, Object> resalt);


}
