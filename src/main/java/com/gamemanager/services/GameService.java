package com.gamemanager.services;

import com.gamemanager.beens.Answer;
import com.gamemanager.beens.Game;
import com.gamemanager.beens.Question;

import java.util.List;

public interface GameService {

    public List<Game> getGames() throws Exception;

    public Game getGame(Long gameId) throws Exception;

    public Question getQuestion(Answer answer) throws Exception;
}
