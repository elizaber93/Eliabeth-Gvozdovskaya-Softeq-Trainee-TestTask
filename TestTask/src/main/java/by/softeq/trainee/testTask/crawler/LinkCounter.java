package by.softeq.trainee.testTask.crawler;

public class LinkCounter {
    private  int linkCount;
    private static LinkCounter linkCounter= new LinkCounter();

    private LinkCounter() {
    }

    public static LinkCounter getInstance() {
        return linkCounter;
    }

    public  int getLinkCount() {
        return this.linkCount;
    }

    public void incrementLinkCount() {
        this.linkCount++;
    }
}
