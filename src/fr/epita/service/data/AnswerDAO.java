package fr.epita.service.data;

import fr.epita.datamodels.Answer;
import fr.epita.service.data.configurations.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {

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
        String createStatement = "CREATE TABLE IF NOT EXISTS ANSWER(ID INT IDENTITY PRIMARY KEY, QUIZID INT, TITLE VARCHAR(255));";
        PreparedStatement cQuery = connection.prepareStatement(createStatement);
        try {
            cQuery.execute();
        }
        catch(Exception e) {
            System.out.println("Problem Creating the Table ... ");
        }
    }
    /*** INSERT A NEW QUESTION TO THE DB */
    public void insert(Answer newAnswer) throws Exception {
        Connection connection = getConnection();
        PreparedStatement iQuery = connection.prepareStatement("INSERT INTO ANSWER(QUIZID, TITLE) values (?,?)", Statement.RETURN_GENERATED_KEYS);
        iQuery.setInt(1, newAnswer.getQuizID());
        iQuery.setString(2, newAnswer.getAnswer());
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
    /*** SEARCH in the DB or Read ALL */
    public List<Answer> search() throws Exception {
        Connection connection = getConnection();
        PreparedStatement sQuery = connection.prepareStatement("SELECT ID, QUIZID, TITLE FROM answer");
        return getAnswers(connection, sQuery);
    }

    public List<Answer> fetch(Integer selectedQuiz) throws Exception {
        Connection connection = getConnection();
        PreparedStatement sQuery = connection.prepareStatement("SELECT ID, QUIZID, TITLE FROM answer WHERE QUIZID=?");
        sQuery.setInt(1, selectedQuiz);
        return getAnswers(connection, sQuery);
    }

    private List<Answer> getAnswers(Connection connection, PreparedStatement sQuery) throws SQLException {
        ResultSet resultSet = sQuery.executeQuery();
        List<Answer> answers = new ArrayList<>();
        while(resultSet.next()) {
            Integer resultID = resultSet.getInt("ID");
            Integer resultQuizID = resultSet.getInt("QUIZID");
            String resultTitle = resultSet.getString("TITLE");
            Answer answer = new Answer(resultQuizID, resultTitle);
            answer.setAnswerID(resultID);
            answer.setQuizID(resultQuizID);
            answer.setAnswer(resultTitle);
            answers.add(answer);
        }
        connection.close();
        return answers;
    }

}