package fr.epita.businesslayer;

import fr.epita.datamodels.Answer;
import fr.epita.datamodels.Question;
import fr.epita.service.data.AnswerDAO;
import fr.epita.service.data.fileparsers.AnswerFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseUnsignedInt;

public class OpenQuiz {

    private Integer selectQuiz;
    private List<Question> questions;
    private Scanner scanner;

    public OpenQuiz(Integer selectQuiz, List<Question> questions, Scanner scanner) throws Exception {
        this.selectQuiz = selectQuiz;
        this.questions = questions;
        this.scanner = scanner;

        List<Answer> answerList = new ArrayList<Answer>();
        AnswerDAO answerDAO = new AnswerDAO();
        answerDAO.create();
        AnswerFileReader answerReader = new AnswerFileReader();
        List<List<String>> fetchedAnswers = answerReader.fetchAnswers();
        for(List<String> fetchedAnswer: fetchedAnswers) answerDAO.insert(new Answer(parseUnsignedInt(fetchedAnswer.get(0)), fetchedAnswer.get(1)));

        List<Answer> answers = new ArrayList<>();
        for (Answer answer : answers = answerDAO.fetch(selectQuiz)) ;
        String userAnswer;
        Integer score = 0;


        for (int i = 0; i < questions.size(); i++) {
            System.out.println(questions.get(i).getQuestion());
            System.out.println("Level of Difficulty: " + questions.get(i).getDifficulty());

            userAnswer = scanner.nextLine();
            if (userAnswer.equals(answers.get(i).getAnswer())) {
                System.out.println("\uD83D\uDDF8 Correct Answer ");
                score++;
            } else System.out.println("Wrong Answer");
            System.out.println("Score: " + score);
            System.out.println("___________________");
        }
        System.out.println("You Have Finished The Quiz.");
        System.out.println("Total Score: " + score + " out of " + questions.size());

    }
}