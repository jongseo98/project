package project.project3;

import java.util.Objects;

public class RecommendArgument {

	private String gender;
    private String age;
    private String occupation;
    private String genres;


	public RecommendArgument(String gender, String age, String occupation, String genres) {
		this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.genres = genres;
	}

	public String getGender() {
		return gender;
	}

	public String getAge() {
		return age;
	}
    public String getOccupation() {
		return occupation;
	}
    public String getGenres() {
		return genres;
	}

    @Override
    public String toString() {
      return "RecommendArgument{" + "gender= " + this.gender + ", age= " + this.age + ", occupation= " + this.occupation + ", genres= " + this.genres + "}";
    }
}