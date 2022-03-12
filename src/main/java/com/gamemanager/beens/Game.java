package com.gamemanager.beens;

import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Game {
    // https://opentdb.com/api.php?amount=10&category=16&difficulty=easy&type=boolean

    static AtomicLong gameCount = new AtomicLong();
    long gameId;
    Integer category;
    String difficulty;
    Integer amount;
    String triviaUrl =  "https://opentdb.com/api.php?amount=10&type=multiple";

    private AtomicInteger questionCount = new AtomicInteger();
    Vector<Question> questionList;

    public Game() {
        this.gameId = Game.gameCount.getAndIncrement();
        questionList = new Vector<>();
    }

    public Game(long gameCount, long gameId, Integer category, String difficulty, Integer amount) {
        this.gameId = Game.gameCount.getAndIncrement();
        questionList = new Vector<>();

        this.category = category;
        this.difficulty = difficulty;
        this.amount = amount;

    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public static AtomicLong getGameCount() {
        return gameCount;
    }

    public static void setGameCount(AtomicLong gameCount) {
        Game.gameCount = gameCount;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void addQuestion(GameJsonMap.Question questionMap ){

        Question question = new Question(gameId, questionCount.getAndIncrement(),
                questionMap.category, questionMap.type, questionMap.difficulty,
                questionMap.question, questionMap.correct_answer, List.of(questionMap.incorrect_answers) );
        getQuestionList().add(question);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId == game.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId);
    }
}
