package by.softeq.trainee.testTask;

import by.softeq.trainee.testTask.entity.Branch;
import by.softeq.trainee.testTask.dao.TXTFileReader;
import by.softeq.trainee.testTask.service.ReportService;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String seed = "https://en.wikipedia.org/wiki/Elon_Musk";
        String outputFile = "report.csv";
        String topReportFile = "top_report.csv";

        Branch tree = null;
        Map<String, List<Integer>> result = new LinkedHashMap<>();
        List<String> inputData = null;
        List<Integer> counts = new ArrayList<>();
        ReportService reportService = new ReportService();
        Map<String,Integer> statistics = null;
        Map<String,List<Integer>> totalStat = null;
        try {
            inputData = new TXTFileReader().getInputData("inputData.txt");
            tree = new Branch(1, seed);
            totalStat = tree.getTotalStat(inputData);

            reportService.getTopReport(10, topReportFile, inputData, totalStat);
            reportService.getReport(outputFile, inputData, totalStat);
        } catch (IOException e) {
            Logger.getLogger(Main.class).info(e);
        }

    }


}
