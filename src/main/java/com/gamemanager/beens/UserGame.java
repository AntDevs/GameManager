package com.gamemanager.beens;


import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class UserGame {
    String userName;
    Game game;
    Hashtable<Question,Answer> answers = new Hashtable<>();

    public UserGame(String userName, Game game) {
        this.userName = userName;
        this.game = game;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Hashtable<Question, Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Question question, Answer answer) {
        this.answers.put(question, answer);
    }

    public Integer calcPoints(){
        AtomicInteger points = new AtomicInteger(0);

        getAnswers().forEach((q,a)->{
            if ( q.getCorrectAnswer().equals(a) ){
                points.getAndSet(points.get() + q.getPoints());
            }
        });

        return points.get();
    }
}
