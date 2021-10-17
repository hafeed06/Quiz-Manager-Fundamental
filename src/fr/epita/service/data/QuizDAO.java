package fr.epita.service.data;

import fr.epita.datamodels.Quiz;
import fr.epita.service.data.configurations.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

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
        String createStatement = "CREATE TABLE IF NOT EXISTS QUIZ(ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(255), QUIZTYPE VARCHAR(255), QUIZLENGTH INT);";
        PreparedStatement cQuery = connection.prepareStatement(createStatement);
        try {
            cQuery.execute();
        }
        catch(Exception e) {
            System.out.println("Problem Creating the Table ... ");
        }
    }
    /*** INSERT A NEW QUESTION TO THE DB */
    public void insert(Quiz newQuiz) throws Exception {
        Connection connection = getConnection();
        PreparedStatement iQuery = connection.prepareStatement("INSERT INTO QUIZ(TITLE, QUIZTYPE, QUIZLENGTH) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        iQuery.setString(1, newQuiz.getTitle());
        iQuery.setString(2, newQuiz.getQuizType());
        iQuery.setInt(3, newQuiz.getLength());

        try {
            iQuery.execute();
        }
        catch(Exception e) {
            System.out.println("Error adding Quiz ... ");
        }
        ResultSet generatedKeys = iQuery.getGeneratedKeys();
        generatedKeys.next();
        int generatedId = generatedKeys.getInt(1);
        connection.close();
    }
    /*** SEARCH in the DB or Read ALL */
    public List<Quiz> search() throws Exception {
        Connection connection = getConnection();
        PreparedStatement sQuery = connection.prepareStatement("SELECT ID, TITLE, QUIZTYPE, QUIZLENGTH FROM QUIZ");
        ResultSet resultSet = sQuery.executeQuery();
        List<Quiz> quizzes = new ArrayList<>();
        while(resultSet.next()) {
            Integer resultID = resultSet.getInt("ID");
            String resultTitle = resultSet.getString("TITLE");
            String resultQuizType = resultSet.getString("QUIZTYPE");
            Integer resultQuizLength = resultSet.getInt("QUIZLENGTH");
            Quiz quiz = new Quiz(resultTitle, resultQuizType, resultQuizLength);
            quiz.setID(resultID);
            quiz.setTitle(resultTitle);
            quiz.setQuizType(resultQuizType);
            quiz.setLength(resultQuizLength);
            quizzes.add(quiz);
        }
        connection.close();
        return quizzes;
    }
//    /*** SEARCH in the DB or Read ALL */
//    public List<Quiz> fetch() throws Exception {
//        Connection connection = getConnection();
//        PreparedStatement sQuery = connection.prepareStatement("SELECT ID, TITLE, QUIZTYPE, QUIZLENGTH FROM QUIZ WHERE ID = 1");
//        ResultSet resultSet = sQuery.executeQuery();
//        List<Quiz> quizzes = new ArrayList<>();
//        while(resultSet.next()) {
//            Integer resultID = resultSet.getInt("ID");
//            String resultTitle = resultSet.getString("TITLE");
//            String resultQuizType = resultSet.getString("QUIZTYPE");
//            Integer resultQuizLength = resultSet.getInt("QUIZLENGTH");
//            Quiz quiz = new Quiz(resultTitle, resultQuizType, resultQuizLength);
//            quiz.setID(resultID);
//            quiz.setTitle(resultTitle);
//            quiz.setQuizType(resultQuizType);
//            quiz.setLength(resultQuizLength);
//            quizzes.add(quiz);
//        }
//        connection.close();
//        return quizzes;
//    }


//
}


