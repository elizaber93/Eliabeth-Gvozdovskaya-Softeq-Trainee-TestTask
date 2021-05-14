package by.softeq.trainee.testTask.crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

    private static Crawler crawler = new Crawler();
    private Pattern pattern;
    private String word = "";

    private Crawler() {

    }

    public static Crawler getInstance() {
        return crawler;
    }

    public Set<String> getLinkSet(String content, String uri) {
        Set<String> linkSet = new HashSet<>();
        String LINKREGEX = "<a href=\"(/wiki/[()a-zA-Z0-9_/%-]+)";
        Pattern pattern = Pattern.compile(LINKREGEX);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
                String link = matcher.group(1);
                linkSet.add(uri+link);
        }
        return linkSet;
    }

    public String getURI(String seed) {
        if (seed.contains("/wiki")) {
            return seed.substring(0, seed.indexOf("/wiki"));
        } else return "";
    }

    public int getWordInclusion(String content, String word) throws IOException {
        int count = 0;
        if (content != null) {
            if (!this.word.equals(word)) {
                this.word = word;
                this.pattern = Pattern.compile(word);
            }
            Matcher matcher = this.pattern.matcher(content);
            while (matcher.find()) {
                count++;
            }
        }
        return count;
    }

}
