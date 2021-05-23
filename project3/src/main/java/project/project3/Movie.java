package main.java.project.project3;

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

	public String getGenre() {
		return genre;
	}

    public String getImdb() {
		return imdb;
	}
}