package com.gamemanager.services;

import com.gamemanager.beens.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger loger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private ApplicationContext appCntext;

    public User getUser(String userName) throws Exception{
        List<User> users =  getUsers();

        Optional<User> userOp = users.parallelStream().filter(u->u.getUserName().equals(userName)).findFirst();
        if ( userOp.isEmpty() ){
            loger.error(" User not found userName:{} ", userName);
            return addUser(userName);
        }
        return userOp.get();
    }

    private User addUser(String userName) throws Exception{
        User user = new User(userName);
        getUsers().add(user);
        loger.info(" User {} added ", userName);
        return user;
    }

    public List<User> getUsers() throws Exception{
        List<User> users =  (List<User>)appCntext.getBean("Users");
        return users;
    }

    private UserGame getUserGame(String userName, Long gameId) throws Exception{
        User users =  getUser(userName);

        Optional<UserGame> userGameOp = users.getUserGames().parallelStream().filter(ug->ug.getGame().getGameId() == gameId).findFirst();
        if ( userGameOp.isEmpty() ){
            loger.error(" For User:{} Game {} not found ", userName, gameId);
            return null;
        }
        return userGameOp.get();

    }

    public Integer addGameAnswer(Answer answer, String userName) throws Exception{
        Game game = gameService.getGame(answer.getGameId());
        User user = getUser(userName);
        UserGame userGame = addGameAnswer(user, game, answer);
        return userGame.calcPoints();
    }

    public Integer getUserEarnedPoints(String userName) throws Exception{
        User user = getUser(userName);
        Hashtable<Long, Integer>  userEarnedPoints = new Hashtable();
        //user.getUserGames().stream().

        return 10;
    }


    public UserGame getUserGame(User user,long gameId) {
        Optional<UserGame> userGameOp =  user.getUserGames().
                parallelStream().filter(g->g.getGame().getGameId() == gameId).findFirst();

        if ( userGameOp.isEmpty() ){
            return null;
        }
        return userGameOp.get();
    }

    public UserGame addGameAnswer(User user, Game game, Answer answer) {
        UserGame userGame = getUserGame(user, answer.getGameId());
        if ( userGame == null ){
            userGame = new UserGame(user.getUserName(), game );
            user.getUserGames().add(userGame);
        }

        Question question = game.getQuestionList().
                parallelStream().filter(q->q.getQuestionId() == answer.getQuestionId() ).findFirst().get();
        userGame.addAnswer(question, answer );

        return userGame;
    }


}
