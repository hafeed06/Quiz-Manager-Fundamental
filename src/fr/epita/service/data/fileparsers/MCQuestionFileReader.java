package fr.epita.service.data.fileparsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MCQuestionFileReader {

    public MCQuestionFileReader() {
    }
    private static final String COMMA_DELIMITER = ",";

    public List<List<String>> fetchQuestions() throws IOException {
        List<List<String>> qRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("MCQuestions.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                qRecords.add(Arrays.asList(values));
            }
        }
        return qRecords;
    }
}