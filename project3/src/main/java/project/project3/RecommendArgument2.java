package project.project3;

import java.util.Objects;

public class RecommendArgument2 {

	private String title;
    private int limit = 10;

	public RecommendArgument2(String title, int limit) {
		this.title = title;
        this.limit = limit;
	}

	public String getTitle() {
		return title;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit() {
		limit = 10;
	}
}