package by.softeq.trainee.testTask.loader;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentLoader {
    private static ContentLoader contentLoader = new ContentLoader();

    private ContentLoader() {
    }
    public static ContentLoader getInstance() {
        return contentLoader;
    }
    public String getContent(String link) {
        StringBuilder content;
        boolean error;
        int retryCount = 0;
        do{
            content = new StringBuilder();
            error = false;
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(link).openConnection();

                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
                con.setConnectTimeout(50000);
                con.setReadTimeout(50000);

                try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()))){
                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                    {
                        content.append(line);
                    }
                }
            }
            catch(Exception e)
            {
                error = true;
                retryCount++;
            }
        } while (error && retryCount < 5);

        if(error){
            return "";
        }
        return content.toString();
    }

    public String getBody(String content) {
        String scriptRegEx = "<script[ a-z=\"/+]*>[{\"@a-zA-Z:\\\\/., _0-9}\\-()=\\[\\]|;%]+<\\/script>";
        String bodyRegEx = "<body[@{}a-zA-Z =\"-0-9_]+>(.+)</body>";
        Pattern pattern = Pattern.compile(bodyRegEx);
        Matcher matcher = pattern.matcher(content);
        String result = "";
        if (matcher.find()) {
          result = matcher.group(1);
        }
        pattern = Pattern.compile(scriptRegEx);
        matcher = pattern.matcher(result);
        while (matcher.find()) {
            result = result.replace(matcher.group(),"");
        }
        return result;
    }

    public String getText(String link) {
        Pattern pattern = Pattern.compile(">([%&—\\-$()a-zA-Z 0-9\".,:;'\\té\\[\\]Æ?!]+)<");
        Matcher matcher = pattern.matcher(getContent(link));
        StringBuilder text = new StringBuilder();
        while (matcher.find()){
            text.append(matcher.group(1));
        }
        return text.toString();
    }

}
