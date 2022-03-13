package com.gamemanager.beens;

import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class Question {

    long gameId;
    Integer questionId;
    Integer points;
    String category;
    String type;
    String difficulty;
    String question;
    Answer correctAnswer;

    private AtomicInteger answersCount = new AtomicInteger();
    Vector<Answer> answersList;

    public Question(long gameId, Integer questionId, String category, String type, String difficulty, String question, String correctAnswer, List<String> answersList) {
        answersCount.set(0);
        this.gameId = gameId;
        this.questionId = questionId;
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.points = 10;

        this.answersList = new Vector<>(answersList.size() + 1);
        this.correctAnswer =  new Answer(this.gameId, this.questionId, answersCount.getAndIncrement() , correctAnswer);
        this.answersList.add(this.correctAnswer);
        answersList.parallelStream().forEach(a->{
            this.answersList.add( new Answer(this.gameId, this.questionId, answersCount.getAndIncrement() , a) );
        });
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Vector<Answer> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(Vector<Answer> answersList) {
        this.answersList = answersList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return questionId == question.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
