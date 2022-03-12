package com.gamemanager.beens;

import java.util.List;

public class GameJsonMap<T> {

    public static class Question{
        String category;
        String type;
        String multiple;
        String difficulty;
        String question;
        String correct_answer;
        String[] incorrect_answers;

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

        public String getMultiple() {
            return multiple;
        }

        public void setMultiple(String multiple) {
            this.multiple = multiple;
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

        public String getCorrect_answer() {
            return correct_answer;
        }

        public void setCorrect_answer(String correct_answer) {
            this.correct_answer = correct_answer;
        }

        public String[] getIncorrect_answers() {
            return incorrect_answers;
        }

        public void setIncorrect_answers(String[] incorrect_answers) {
            this.incorrect_answers = incorrect_answers;
        }
    }

    Integer response_code;

    List <T> results;

    public Integer getResponse_code() {
        return response_code;
    }

    public void setResponse_code(Integer response_code) {
        this.response_code = response_code;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
