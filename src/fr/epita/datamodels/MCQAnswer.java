package fr.epita.datamodels;

public class MCQAnswer {

    private Integer answerID;
    private Integer questionID;
    private Integer quizID;
    private String Answer;
    private String validity;

    public MCQAnswer(Integer quizID, String Answer, String validity) {
        this.quizID = quizID;
        this.Answer = Answer;
        this.validity = validity;
    }

    public Integer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Integer answerID) {
        this.answerID = answerID;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public Integer getQuizID() {
        return quizID;
    }

    public void setQuizID(Integer quizID) {
        this.quizID = quizID;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }



    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }


}
