package project.project4;

import java.util.Objects;

public class Movie {

	private String title;
    private String genre;
    private String imdb;

	public Movie(String title, String genre, String imdb) {
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