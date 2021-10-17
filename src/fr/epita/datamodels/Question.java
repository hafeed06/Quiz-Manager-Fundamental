package fr.epita.datamodels;

public class Question {

    public static final int DEFAULT_DIFFICULTY = 2;
    public static final String DEFAULT_QUESTION = "Warm Up Question";
    public static final Integer DEFAULT_QUIZID = 1;

    private Integer questionID;
    private Integer quizID = DEFAULT_QUIZID;
    private String question = DEFAULT_QUESTION;
    private Integer difficulty = DEFAULT_DIFFICULTY;

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }


    public Question(Integer quizID, String question, Integer difficulty) {
        this.quizID = quizID;
        this.question = question;
        this.difficulty = difficulty;
    }
    private Integer id;


    public Integer getQuizID() {
        return quizID;
    }

    public void setQuizID(Integer quizID) {
        this.quizID = quizID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    private String[] topics = new String[]{};

}
