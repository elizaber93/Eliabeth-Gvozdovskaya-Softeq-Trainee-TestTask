package by.softeq.trainee.testTask.dao;


import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TXTFileReader {
    public List<String> getInputData(String fileName) throws IOException {
        List<String> result = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            Logger.getLogger(TXTFileReader.class).info(e);
        }
        return result;
    }
}
