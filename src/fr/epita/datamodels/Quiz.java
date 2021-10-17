package fr.epita.datamodels;
public class Quiz {

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    private Integer id;
    private String title;
    private String quizType;
    private Integer length;

    public Quiz(String title, String type, Integer length) {
        this.title = title;
        this.quizType = type;
        this.length = length;
    }

    public void createQuiz() {

    }
}

