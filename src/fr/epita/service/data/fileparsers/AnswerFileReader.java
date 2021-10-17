package fr.epita.service.data.fileparsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnswerFileReader {

    public AnswerFileReader() {
    }
    private static final String COMMA_DELIMITER = ",";

    public List<List<String>> fetchAnswers() throws IOException {
        List<List<String>> aRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Answers.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                aRecords.add(Arrays.asList(values));
            }
        }
        return aRecords;
    }
}