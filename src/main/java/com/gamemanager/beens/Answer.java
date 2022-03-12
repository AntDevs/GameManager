package com.gamemanager.beens;

import java.util.Objects;

public class Answer {
    long gameId;
    Integer questionId;
    Integer answerId;
    String answer;

    public Answer(long gameId, Integer questionId, Integer answerId, String answer) {
        this.gameId = gameId;
        this.questionId = questionId;
        this.answerId = answerId;
        this.answer = answer;
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

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return  answerId == answer.answerId && questionId == answer.questionId && gameId == answer.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }
}
