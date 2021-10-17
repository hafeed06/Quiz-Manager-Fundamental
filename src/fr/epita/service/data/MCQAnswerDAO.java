package fr.epita.service.data;

import fr.epita.datamodels.Answer;
import fr.epita.datamodels.MCQAnswer;
import fr.epita.service.data.configurations.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MCQAnswerDAO {

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
        String createStatement = "CREATE TABLE IF NOT EXISTS MCQANSWER(ID INT IDENTITY PRIMARY KEY, QUIZID INT, TITLE VARCHAR(255), VALIDITY VARCHAR(10));";
        PreparedStatement cQuery = connection.prepareStatement(createStatement);
        try {
            cQuery.execute();
        }
        catch(Exception e) {
            System.out.println("Problem Creating the Table ... ");
        }
    }
    /*** INSERT A NEW QUESTION TO THE DB
     * @param newAnswer*/
    public void insert(MCQAnswer newAnswer) throws Exception {
        Connection connection = getConnection();
        PreparedStatement iQuery = connection.prepareStatement("INSERT INTO MCQANSWER(QUIZID, TITLE, VALIDITY) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        iQuery.setInt(1, newAnswer.getQuizID());
        iQuery.setString(2, newAnswer.getAnswer());
        iQuery.setString(3, newAnswer.getValidity());
        try {
            iQuery.execute();
        }
        catch(Exception e) {
            System.out.println("Error adding Answer ... ");
        }
        ResultSet generatedKeys = iQuery.getGeneratedKeys();
        generatedKeys.next();
        int generatedId = generatedKeys.getInt(1);
        connection.close();
    }
    /*** SEARCH in the DB or Read ALL */
    public List<MCQAnswer> search() throws Exception {
        Connection connection = getConnection();
        PreparedStatement sQuery = connection.prepareStatement("SELECT ID, QUIZID, TITLE, VALIDITY FROM MCQANSWER");
        return getAnswers(connection, sQuery);
    }

    public List<MCQAnswer> fetch(Integer selectedQuiz) throws Exception {
        Connection connection = getConnection();
        PreparedStatement sQuery = connection.prepareStatement("SELECT ID, QUIZID, TITLE, VALIDITY FROM MCQANSWER WHERE QUIZID=?");
        sQuery.setInt(1, selectedQuiz);
        return getAnswers(connection, sQuery);
    }

    private List<MCQAnswer> getAnswers(Connection connection, PreparedStatement sQuery) throws SQLException {
        ResultSet resultSet = sQuery.executeQuery();
        List<MCQAnswer> answers = new ArrayList<>();
        while(resultSet.next()) {
            Integer resultID = resultSet.getInt("ID");
            Integer resultQuizID = resultSet.getInt("QUIZID");
            String resultTitle = resultSet.getString("TITLE");
            String resultValidity = resultSet.getString("VALIDITY");
            MCQAnswer answer = new MCQAnswer(resultQuizID, resultTitle, resultValidity);
            answer.setAnswerID(resultID);
            answer.setQuizID(resultQuizID);
            answer.setAnswer(resultTitle);
            answer.setValidity(resultValidity);
            answers.add(answer);
        }
        connection.close();
        return answers;
    }

}