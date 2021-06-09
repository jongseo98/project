package project.project4.result;

import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "results")
public class Result {

	private String title;
    private String genre;
    private String imdb;

    public Result(){}

	public Result(String title, String genre, String imdb) {
		this.title = title;
        this.genre = genre;
        this.imdb = imdb;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

}