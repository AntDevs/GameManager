package com.gamemanager.services;

import com.gamemanager.beens.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;
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

        Hashtable<String, Object> ret = new Hashtable<>();

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

        Hashtable<String, Object> ret = new Hashtable<>();
        ret.put("GameId", gameId);
        try {

            List<UserGame> userGameList =
                    userService.getUsers().parallelStream().flatMap(u->u.getUserGames().stream())
                            .filter(ug->ug.getGame().getGameId() == gameId).collect(Collectors.toList());

            if ( userGameList.size() == 0 ) {
                loger.info("No player has played this game:{}", gameId );
                ret.put("MSG", "No player has played this game" );
                return ret;
            }

            Optional<UserGame> userGameMaxOp = userGameList.parallelStream()
                                    .max((a, b)->a.calcPoints().compareTo(b.calcPoints()));
            UserGame userGameMax;
            if ( userGameMaxOp.isPresent() ){
                userGameMax = userGameMaxOp.get();
                ret.put("Max Points", userGameMax.calcPoints());
                loger.info("User {}} has the highest score of {} in game:{}", userGameMax.getUserName(),
                                                                            userGameMax.calcPoints(), gameId);
                ret.put("User Name", userGameMax.getUserName());
            }

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
