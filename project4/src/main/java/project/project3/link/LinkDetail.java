package project.project4.link;

import java.util.Objects;

public class LinkDetail {

    private String linkId;
	private String imdbId;

    public String getLinkId() {
        return linkId;
    }

    public void setId(String linkId) {
        this.linkId = linkId;
    }

	public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}