package fr.epita.datamodels;

public class Answer {

    private static final Integer DEFAULT_QUIZID = 1;



    public Answer(Integer quizID, String answer) {
        this.quizID = quizID;
        this.answer = answer;
    }

    public Integer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Integer answerID) {
        this.answerID = answerID;
    }

    public Integer getQuizID() {
        return quizID;
    }

    public void setQuizID(Integer quizID) {
        this.quizID = quizID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private Integer answerID;
    private Integer quizID = DEFAULT_QUIZID;
    private String answer;

}
