package fr.epita.launcher;
import fr.epita.businesslayer.MCQuiz;
import fr.epita.businesslayer.OpenQuiz;
import fr.epita.datamodels.Question;
import fr.epita.datamodels.Quiz;
import fr.epita.service.data.*;
import fr.epita.service.data.fileparsers.QuestionFileReader;
import fr.epita.service.data.fileparsers.QuizFileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseUnsignedInt;

public class Launcher {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        // *************** QUIZ PART *************** //
        /* Declaring a list of quizzes which we will use to create quiz instances from the file */
        /* For things to remain clean, we made a Quiz-reader that reads from a CSV File separately
        We will use the info generated from said file and initiate a new Quiz instance for each record */

        QuizDAO quizDAO = new QuizDAO();
        quizDAO.create();
        QuizFileReader quizReader = new QuizFileReader();
        List<List<String>> fetchedQuizzes = quizReader.fetchQuiz();
        for(List<String> fetchedQuiz: fetchedQuizzes) quizDAO.insert(new Quiz(fetchedQuiz.get(0), fetchedQuiz.get(1), parseInt(fetchedQuiz.get(2))));

        List<Quiz> quizzes = new ArrayList<>();
        for (Quiz resultQuiz : quizzes = quizDAO.search());

        // *************** QUESTIONS PART *************** //
        // QUESTION TABLE CREATION AND INSERTION OF RECORDS FROM OUR FILE

        List<Question> questionList = new ArrayList<Question>();
        QuestionDAO questionDAO = new QuestionDAO();
        questionDAO.create();
        QuestionFileReader questionReader = new QuestionFileReader();
        List<List<String>> fetchedQuestions = questionReader.fetchQuestions();
        for(List<String> fetchedQuestion: fetchedQuestions) questionDAO.insert(new Question(parseUnsignedInt(fetchedQuestion.get(0)), fetchedQuestion.get(1),parseUnsignedInt(fetchedQuestion.get(2))));
        // *************** START OF THE QUIZ *************** //
        System.out.println("Please Select Which Quiz You want to take: ");
        Integer selectQuiz;
        String message;
        for(Quiz quiz: quizzes) {
            message = quiz.getID() + " - " + quiz.getTitle() + " | Type: " + quiz.getQuizType() + " | Length: " + quiz.getLength();
            System.out.println(message);
        }
        selectQuiz = scanner.nextInt();
        String quizType = quizzes.get(selectQuiz).getQuizType();
        scanner.nextLine(); // This is to fix the issue of nextInt causing the following nextLine prompt to be ignored
        // Fetching Questions of the selected Quiz
        List<Question> questions = new ArrayList<>();
        for (Question question : questions = questionDAO.fetch(selectQuiz));

        // Directing User to the proper class to handle the logic depending on their choice
        if(quizType.equals("Open")) new OpenQuiz(selectQuiz, questions, scanner);
        else if(quizType.equals("MCQ")) new MCQuiz(selectQuiz, questions, scanner);
        else System.out.println("Invalid Choice");
    }
}