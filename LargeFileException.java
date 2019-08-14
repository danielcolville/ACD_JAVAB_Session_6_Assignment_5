package session6;

public class LargeFileException extends Exception {
	public LargeFileException() {
		super("LOG FILE IS TOO LARGE TO BE WRITTEN");
	}
}
