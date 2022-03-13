package com.gamemanager.services;

import com.gamemanager.beens.Answer;
import com.gamemanager.beens.Game;
import com.gamemanager.beens.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImlp implements GameService {

    private static final Logger loger = LoggerFactory.getLogger(GameServiceImlp.class);

    @Autowired
    private ApplicationContext appCntext;

    public List<Game> getGames(){
        List<Game> games = (List<Game>) appCntext.getBean("Games");
        return games;
    }

    public synchronized Game getGame(Long gameId) throws Exception{
        Optional<Game> gameOp =  getGames().parallelStream().filter(g-> g.getGameId() == gameId ).findFirst() ;
        if ( gameOp.isEmpty() ){
            loger.error(" Game not found gameId:{} ", gameId);
            throw new Exception( " Game not found gameId:" + gameId);
        }
        return gameOp.get();
    }

    public Question getQuestion(Answer answer) throws Exception{

        Game game = getGame(answer.getGameId());
        Optional<Question> questionOp =  game.getQuestionList().
                parallelStream().filter(q->q.getQuestionId().equals(answer.getQuestionId()) ).findFirst();

        if ( questionOp.isEmpty() ){
            loger.error(" Question not found gameId:{}, QuestionId:{} ", answer.getGameId(), answer.getQuestionId());
            return null;
        }
        return questionOp.get();
    }


}
