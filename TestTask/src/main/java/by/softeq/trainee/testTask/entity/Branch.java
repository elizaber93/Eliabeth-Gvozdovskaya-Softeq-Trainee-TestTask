package by.softeq.trainee.testTask.entity;

import by.softeq.trainee.testTask.crawler.Crawler;
import by.softeq.trainee.testTask.crawler.LinkCounter;
import by.softeq.trainee.testTask.loader.ContentLoader;

import java.io.IOException;
import java.util.*;

public class Branch {
    private final String seed;
    private Set<Branch> branches = null;
    private final int  depth;
    private final int maxDepth = 8;
    private final int maxLinkCount = 10000;
    private int pageWordCount;
    private int totalWordPageCount;

    public Branch(int depth, String seed) throws IOException {
        this.depth = depth;
        this.seed = seed;
        LinkCounter.getInstance().incrementLinkCount();
        load();
    }


    public void load() throws IOException {

        Crawler crawler = Crawler.getInstance() ;
        String uri = crawler.getURI(seed);
        if (this.depth < this.maxDepth && LinkCounter.getInstance().getLinkCount() < this.maxLinkCount) {
            Set<String> links = crawler.getLinkSet(getContent(), uri);
            this.branches = new HashSet<>();
            int depth = this.depth + 1;
            for (String s : links) {
                if (LinkCounter.getInstance().getLinkCount() == this.maxLinkCount) {
                    break;
                }
                this.branches.add(new Branch(depth, s));
            }
        } else if (this.depth == this.maxDepth || LinkCounter.getInstance().getLinkCount() == this.maxLinkCount) {
            this.branches = null;
        }
    }

    public String getContent() {
        return ContentLoader.getInstance().getContent(this.seed);
    }

    public String getText() {
        return ContentLoader.getInstance().getText(this.seed);
    }

    public int getWordCount(String word) throws IOException {
        Crawler crawler = Crawler.getInstance();
        int count = crawler.getWordInclusion(getText(), word);
        this.pageWordCount = count;
        this.totalWordPageCount += count;
        if (this.branches == null) {
            return count;
        } else {
            for (Branch link : this.branches) {
                count += link.getWordCount(word);
            }
        }
        return count;
    }
    public int getLinkCount() {
        int count = 0;
        if (this.branches == null) {
            return 0;
        } else {
            count = this.branches.size();
            for (Branch link : this.branches) {
                count += link.getLinkCount();
            }
        }
        return count;
    }

    public Map<String,List<Integer>> getTotalStat(List<String> words) throws IOException {
        Map<String,List<Integer>> result = new LinkedHashMap<>();
        List<Integer> counts = new ArrayList<>();
        int totalCount = 0;
        int count = 0;
        for (String word : words) {
            count = Crawler.getInstance().getWordInclusion(getText(), word);
            counts.add(count);
            totalCount += count;
        }
        counts.add(totalCount);
        result.put(this.seed,counts);
        if (this.branches != null) {
            for (Branch branch : branches) {
                result.putAll(branch.getTotalStat(words));
            }
        }
        return result;
    }
}
