package project.project4.movie_poster;

import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "movie_posters")
public class MoviePoster {

    @Id
    private String id;
	private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}