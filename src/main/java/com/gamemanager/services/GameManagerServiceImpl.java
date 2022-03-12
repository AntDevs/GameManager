package com.gamemanager.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemanager.beens.*;
import com.gamemanager.config.LoadDatabaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameManagerServiceImpl implements GameManagerService {

    private static final Logger loger = LoggerFactory.getLogger(GameManagerServiceImpl.class);

    final String ERROR_FLD = "Error";

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Override
    public Hashtable<String, Object> AnswerQuestion(Answer answer, String userName) {

        Hashtable<String, Object> ret = new  Hashtable<String, Object>();

        try {
            Integer earnedPoints =  userService.addGameAnswer(answer, userName);
            ret.put("GameEarnedPoints", earnedPoints );

            Question question = gameService.getQuestion(answer);
            if ( question.getCorrectAnswer().equals(answer) ){
                ret.put("QuestionPoints", question.getPoints() );
                ret.put("AnswerStatus", "Correct" );
            } else {
                ret.put("AnswerStatus", "Not right" );
                ret.put("QuestionPoints", 0 );
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.put(ERROR_FLD, e.getMessage());
        }

        return ret;
    }

    @Override
    public Hashtable<String, Object> getLeaderboard(Long gameId) {

        Hashtable<String, Object> ret = new Hashtable<String, Object>();
        ret.put("GameId", gameId);
        try {
            List<UserGame> userGameList =
                    userService.getUsers().parallelStream().map(u->
                            u.getUserGames().parallelStream().filter(ug->ug.getGame().getGameId() == gameId).findFirst().get()
                        ).collect(Collectors.toList());

            if ( userGameList.size() == 0 ) {
                loger.info("No player has played this game:{}", gameId );
                ret.put("MSG", "No player has played this game" );
                return ret;
            }

            UserGame userGameMax = userGameList.parallelStream().max((a, b)->a.calcPoints().compareTo(b.calcPoints())).get();

            userGameMax.calcPoints();

            ret.put("Max Points", userGameMax.calcPoints());
            loger.info("User {}} has the highest score of {} in game:{}", userGameMax.getUserName(),
                                                                        userGameMax.calcPoints(), gameId);
            ret.put("User Name", userGameMax.getUserName());

        } catch (Exception e) {
            ret.put(ERROR_FLD, e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public boolean isError(Hashtable<String, Object> resalt) {
        return resalt.containsKey(ERROR_FLD);
    }

}
