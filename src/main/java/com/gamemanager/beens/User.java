package com.gamemanager.beens;

import java.util.Vector;

public class User {
    String userName;

    Vector<UserGame> userGames = new Vector<>();

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Vector<UserGame> getUserGames() {
        return userGames;
    }

    public void addUserGames(UserGame userGame) {
        getUserGames().add(userGame);
    }

}
