package loader;

public class LoaderException extends Exception {

    public LoaderException() {
        super();
    }
    public LoaderException(String message) {
        super(message);
    }
    public LoaderException(Exception e) {
        super(e);
    }
    public LoaderException(String message, Exception e) {
        super(message, e);
    }
}
