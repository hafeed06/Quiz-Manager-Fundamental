package fr.epita.businesslayer;

import fr.epita.datamodels.MCQAnswer;
import fr.epita.datamodels.Question;
import fr.epita.service.data.MCQAnswerDAO;
import fr.epita.service.data.fileparsers.MCQAnswerFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseUnsignedInt;

public class MCQuiz {

    Integer selectQuiz;
    List<Question> questions;
    Scanner scanner;

    public MCQuiz(Integer selectQuiz, List<Question> questions, Scanner scanner) throws Exception {

        this.selectQuiz = selectQuiz;
        this.questions = questions;
        this.scanner = scanner;

        // *************** ANSWERS PART *************** //
        // ANSWERS TABLE CREATION AND INSERTION OF RECORDS FROM OUR FILE
        List<MCQAnswer> mcqAnswerList = new ArrayList<MCQAnswer>();

        MCQAnswerDAO mcqAnswerDAO = new MCQAnswerDAO();
        mcqAnswerDAO.create();

        MCQAnswerFileReader mcqAnswerReader = new MCQAnswerFileReader();
        List<List<String>> mcqFetchedAnswers = mcqAnswerReader.fetchAnswers();
        for (List<String> fetchedAnswer : mcqFetchedAnswers)
            mcqAnswerDAO.insert(new MCQAnswer(parseUnsignedInt(fetchedAnswer.get(0)), fetchedAnswer.get(1), fetchedAnswer.get(2)));
        List<MCQAnswer> mcqAnswers = new ArrayList<>();
        for (MCQAnswer mcqAnswer : mcqAnswers = mcqAnswerDAO.fetch(selectQuiz)) ;
        Integer score = 0;
        Integer userChoice;
        // Start and End of the inner loop to show the options
        int s = 0;
        int e = 3;
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(questions.get(i).getQuestion() + " : ");
            System.out.println("Level of Difficulty: " + questions.get(i).getDifficulty());
            for (int j = s, index = 1; j <= e; j++, index++) {
                System.out.println(index + " : " + mcqAnswers.get(j).getAnswer());
            }
            userChoice = scanner.nextInt();
            // User choice is between 1 and 4, while the result of the our answers list have different indexes. This is the solution for that
            switch (userChoice) {
                case 1:
                    userChoice = e - 3;
                    break;
                case 2:
                    userChoice = e - 2;
                    break;
                case 3:
                    userChoice = e - 1;
                    break;
                case 4:
                    userChoice = e;
                    break;
            }
            System.out.println("You chose: " + mcqAnswers.get(userChoice).getAnswer());
            if (mcqAnswers.get(userChoice).getValidity().equals("t")) {
                System.out.println("\uD83D\uDDF8 Correct Answer ");
                score++;
            } else System.out.println("✕ Wrong Answer");
            s = s + 4;
            e = e + 4;
            System.out.println("_________________________");
        }
        System.out.println("You Have Finished The Quiz.");
        System.out.println("Total Score: " + score + " out of " + questions.size());
        scanner.close();

    }
}
