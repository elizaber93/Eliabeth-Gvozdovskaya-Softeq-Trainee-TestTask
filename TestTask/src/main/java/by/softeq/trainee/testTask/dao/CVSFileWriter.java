package by.softeq.trainee.testTask.dao;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CVSFileWriter {
    public CVSFileWriter() {

    }
    public void addLineToCSV(String fileName, String seed, List<Integer> counts) throws IOException {
        BufferedWriter bw = null;
        String SEPARATOR=";";
        try
        {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), "UTF-8"));
            StringBuilder line = new StringBuilder();
            line.append(seed).append(SEPARATOR);
            for (Integer count : counts) {
                line.append(count).append(SEPARATOR);
            }
            bw.write(line.toString());
            bw.newLine();
        } catch (IOException e) {
            Logger.getLogger(CVSFileWriter.class).info(e);
        } finally {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        }
    }

    public void writeTitleToCSV(String fileName, List<String> words) throws IOException {
        BufferedWriter bw = null;
        String SEPARATOR=";";
        try
        {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));

            StringBuilder title = new StringBuilder().append("Seed").append(SEPARATOR);
            for (String word : words) {
                title.append(word).append(SEPARATOR);
            }
            bw.write(title.toString());
            bw.newLine();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        }
    }
}
