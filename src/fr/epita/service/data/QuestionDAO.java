package fr.epita.service.data;

import fr.epita.datamodels.Question;
import fr.epita.service.data.configurations.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    private Connection getConnection() throws Exception {

        Config conf = Config.getInstance();
        Connection connection = DriverManager.getConnection(conf.getConf("db.url"), conf.getConf("db.user"), conf.getConf("db.password"));
        /* System.out.println(connection.getSchema()); */

        if(!"PUBLIC".equals(connection.getSchema())) {
            throw new Exception("The connexion to the database failed ! ");
        }
        return connection;
    }

    /*** CREATE THE TABLE QUESTION IN THE DB */
    public void create() throws Exception {
        Connection connection = getConnection();
        String createStatement = "CREATE TABLE IF NOT EXISTS QUESTION(ID INT IDENTITY PRIMARY KEY, QUIZID INT, TITLE VARCHAR(255), DIFFICULTY INT);";
        PreparedStatement cQuery = connection.prepareStatement(createStatement);
        try {
            cQuery.execute();
        }
        catch(Exception e) {
            System.out.println("Problem Creating the Table ... ");
        }
    }
    /*** INSERT A NEW QUESTION TO THE DB */
    public void insert(Question newQuestion) throws Exception {
        Connection connection = getConnection();
        PreparedStatement iQuery = connection.prepareStatement("INSERT INTO QUESTION(QUIZID, TITLE, DIFFICULTY) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        iQuery.setInt(1, newQuestion.getQuizID());
        iQuery.setString(2, newQuestion.getQuestion());
        iQuery.setInt(3, newQuestion.getDifficulty());
        try {
            iQuery.execute();
        }
        catch(Exception e) {
            System.out.println("Error adding Question ... ");
        }
        ResultSet generatedKeys = iQuery.getGeneratedKeys();
        generatedKeys.next();
        int generatedId = generatedKeys.getInt(1);
        connection.close();
    }
    /*** FETCH ALL FROM DB */
    public List<Question> search() throws Exception {
        Connection connection = getConnection();
        PreparedStatement sQuery = connection.prepareStatement("SELECT ID, QUIZID, TITLE, DIFFICULTY FROM question");
        return getQuestions(connection, sQuery);
    }
    /*** FETCH SPECIFIC QUIZ RECORDS */
    public List<Question> fetch(Integer selectedQuiz) throws Exception {
        Connection connection = getConnection();
        PreparedStatement sQuery = connection.prepareStatement("SELECT ID, QUIZID, TITLE, DIFFICULTY FROM question WHERE QUIZID = ?");
        sQuery.setInt(1, selectedQuiz);
        return getQuestions(connection, sQuery);
    }
    /*** FETCH LAST RECORDS */
    public List<Question> getLast() throws Exception {
        Connection connection = getConnection();
        PreparedStatement sQuery = connection.prepareStatement("SELECT ID, QUIZID, TITLE, DIFFICULTY FROM question ORDER BY ID DESC LIMIT 1");
        return getQuestions(connection, sQuery);
    }

    private List<Question> getQuestions(Connection connection, PreparedStatement sQuery) throws SQLException {
        ResultSet resultSet = sQuery.executeQuery();
        List<Question> questions = new ArrayList<>();
        while(resultSet.next()) {
            Integer resultID = resultSet.getInt("ID");
            Integer resultQuizID = resultSet.getInt("QUIZID");
            String resultTitle = resultSet.getString("TITLE");
            Integer resultDifficulty = resultSet.getInt("DIFFICULTY");
            Question question = new Question(resultQuizID, resultTitle, resultDifficulty);
            question.setQuestionID(resultID);
            question.setDifficulty(resultDifficulty);
            question.setQuestion(resultTitle);
            questions.add(question);
        }
        connection.close();
        return questions;
    }

}