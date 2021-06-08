package project.project4.link;

import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "links")
public class Link {

    @Id
    private String id;
	private String imdbId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

}