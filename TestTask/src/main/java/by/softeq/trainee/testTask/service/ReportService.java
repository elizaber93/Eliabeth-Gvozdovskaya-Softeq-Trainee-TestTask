package by.softeq.trainee.testTask.service;

import by.softeq.trainee.testTask.dao.CVSFileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportService {
    public ReportService() {

    }

    public void getReport(String fileName, List<String> words, Map<String,List<Integer>> result) throws IOException {
        CVSFileWriter cvsFileWriter = new CVSFileWriter();
        cvsFileWriter.writeTitleToCSV(fileName, words);
        for (String seed : result.keySet()) {
            cvsFileWriter.addLineToCSV(fileName, seed, result.get(seed));
        }

    }

    public void getTopReport(int topCount, String fileName, List<String> words, Map<String,List<Integer>> result) throws IOException {
        CVSFileWriter cvsFileWriter = new CVSFileWriter();
        cvsFileWriter.writeTitleToCSV(fileName, words);



        List<Map.Entry<String,List<Integer>>> list = new ArrayList<>(result.entrySet());
        list.sort((o1, o2) -> o2.getValue().get(o2.getValue().size()-1) - o1.getValue().get(o1.getValue().size()-1));

        List<Integer> totalCount = new ArrayList<>();
        int entryCount = 0;
        for (Map.Entry<String,List <Integer>> entry : list) {
            if (entryCount == topCount) {
                break;
            }
            cvsFileWriter.addLineToCSV(fileName, entry.getKey(), entry.getValue());
            entryCount++;

        }

    }
}
