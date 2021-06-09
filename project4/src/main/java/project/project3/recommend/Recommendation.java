package project.project4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class Recommendation {

    public static final String[] occup_list = new String[]{
        "other","academic/educator", "artist", "clerical/admin", "college/gradstudent", "customerservice", 
        "doctor/healthcare", "executive/managerial", "farmer", "homemaker", "K-12student", "lawyer", 
        "programmer", "retired", "sales/marketing", "scientist", "self-employed", "technician/engineer", 
        "tradesman/craftsman", "unemployed", "writer"
    };
    public static final String[] movie_genre_list = new String[]{
        "Action", "Adventure", "Animation", "Children's", "Comedy", "Crime", "Documentary", "Drama", "Fantasy", 
        "Film-Noir", "Horror", "Musical", "Mystery", "Romance", "Sci-Fi", "Thriller", "War", "Western"
    };

    private String gender;
    private int age;
    private String occupation;
    private String genre;
    private String title;
    private int limit;

    private boolean err;
    private String err_msg;

    private ArrayList<Movie> result = new ArrayList<Movie>();

    public ArrayList<Movie> getResult() {
        return result;
    }

    public Recommendation(String title, int limit) {
        this.title = title;
        this.limit = limit;

        this.gender = "";
        this.age = 0;
        this.occupation = "";
        this.genre = "";

        this.err = false;
        this.err_msg = "";
    }

    public Recommendation(String gender, String age, String occupation, String genre) {
        this.gender = gender;
        if (age.equals(""))
            this.age = 0;
        else
            this.age = Integer.parseInt(age);
        this.occupation = occupation;
        this.genre = genre;

        this.title = "";
        this.limit = 10;

        this.err = false;
        this.err_msg = "";
    }

    @Override
	public String toString() {
        String s = "";
        if (err) {
            s += err_msg;
        } else {
            s += "[{\n";
            for (int i=0; i<limit; i++) {
                Movie movie = result.remove(0);
                s += "  \"title\": " + "\"" + movie.getTitle() + "\"" + ",\n";
                s += "  \"genre\": " + "\"" + movie.getGenre() + "\"" + ",\n";
                s += "  \"imdb\": " + "\""+ movie.getImdb() + "\"" + ",\n";
                
                if (i == limit -1)
                    s += "}]\n";
                else {
                    s += "},\n";
                    s += "{\n";
                }
            }
        }
    	return s;
	}

    // private static String [][] movie_list = new String[limit][3];
    // private static String [] link_list = new String[limit];

    // gender:age:occup => user.dat 파일에서 일치하는 user 를 userID list에 추가
    // rating.dat 파일에서 해당 user가 쓴 평점을 통해 movieGroup에 (movieId, rating_sum, rating_num, rating_avg) 추가
    // movieGroup을 rating_avg 순으로 정렬, 같다면 rating_num 순으로 내림차순 정렬 
    // 만약 genre도 input 있을때, movieGroup 안에 있는 영화중 해당 장르에 속한 영화
    // genre input 없을때,  movieGroup 안에 있는 영화중 rating_num > count/rating_count 를 넘은 영화를 추출해서 movie_list 에 (movieId, movieName)으로 저장
    // links.dat 파일에서 movie_list에 해당하는 영화의 imdbID를 link_list에 추출
    // movie_list 와 link_list를 10개 출력
    public void getMovielist() {
        try{
            err = false;
            //파일 객체 생성
            File moviefile = new File("/root/project/project4/src/main/resources/static/data/ml-1m/movies.dat");
            File ratingfile = new File("/root/project/project4/src/main/resources/static/data/ml-1m/ratings.dat");
            File userfile = new File("/root/project/project4/src/main/resources/static/data/ml-1m/users.dat");
            File linkfile = new File("/root/project/project4/src/main/resources/static/data/ml-1m/links.dat");
            //입력 버퍼 생성
            BufferedReader moviebuf = new BufferedReader(new FileReader(moviefile));
            BufferedReader ratingbuf = new BufferedReader(new FileReader(ratingfile));
            BufferedReader userbuf = new BufferedReader(new FileReader(userfile));
            BufferedReader linkbuf = new BufferedReader(new FileReader(linkfile));

            String [][] movie_list = new String[limit][3];
            String [] link_list = new String[limit];
            int titleId=0; //w  

            boolean gender_null = false;
            boolean age_null = false;
            boolean occup_null = false;
            boolean genre_null = false;
            boolean limit_null = false;

            if (gender == null || gender.isEmpty()) {
                gender_null = true;
            }
            if (age == 0) {
                age_null = true;
            }
            if (occupation == null || occupation.isEmpty()) {
                occup_null = true;
            }
            if (genre == null || genre.isEmpty()) {
                genre_null = true;
            }
            if (title == null || title.isEmpty()) {
                limit_null = true;
            }

            // title 이랑 limit 들어올 때
            if (!limit_null) {
                System.out.println(title);
                genre_null = false;
                String moviedat_line = "";
                while((moviedat_line = moviebuf.readLine()) != null){
                    String[] moviewords = moviedat_line.split("::");
                    String movieName = moviewords[1];
                    titleId =Integer.parseInt(moviewords[0]);//w
                    // System.out.println(movieName);
                    if (title.equalsIgnoreCase(movieName)) {
                        genre = moviewords[2];
                        break;
                    }
                }
                System.out.println(genre);
                System.out.println(limit);
                moviebuf = new BufferedReader(new FileReader(moviefile));
            } 
            // gender 이 비정상일때
            if (!gender_null) {
                if (!(gender.equalsIgnoreCase("F")) && !(gender.equalsIgnoreCase("M"))) {
                    err = true;
                    err_msg = "Error. User gender is F or M";
                    return;
                }
            }

            // age 이 비정상일때
            if (!age_null) {
                if (age <= 0) {
                    err = true;
                    err_msg = "Error. User age cannot be under 0";
                    return;
                }
            }

            // occupation이 occup_list에 없는 경우
            if (!occup_null) {
                boolean occup_bool = false;
                for (String occup: occup_list) {
                    if (occup.contains("/")) {
                        String[] occups = occup.split("/");
                        for (String s: occups) {
                            if (occupation.equalsIgnoreCase(s)) {
                                occup_bool = true;
                                break;
                            }
                        }
                    } else {
                        if (occupation.equalsIgnoreCase(occup))
                            occup_bool = true;
                    }
                    if (occup_bool)
                        break;
                }
                if (!occup_bool) {
                    err = true;
                    err_msg = "Error. The occupation does not exist";
                    return;
                }
            }

            // genre이 genre_list에 없는 경우
            if (!genre_null) {
                System.out.println(genre);
                boolean genre_bool = false;
                if (genre.contains("|")) {
                    String[] input_genre_list = genre.split("\\|");
                    for (String input: input_genre_list) {
                        genre_bool = false;
                        for (String g: movie_genre_list) {
                            if (input.equalsIgnoreCase(g)) {
                                genre_bool = true;
                                break;
                            }
                        }
                    }
                } else {
                    for (String g: movie_genre_list) {
                        if (genre.equalsIgnoreCase(g)) {
                            genre_bool = true;
                            break;
                        }
                    }
                }
                if (!genre_bool) {
                    err = true;
                    err_msg = "Error. The movie genre does not exist";
                    return;
                }
            }

            // *  1:  "Under 18" * 18:  "18-24" * 25:  "25-34" * 35:  "35-44" * 45:  "45-49" * 50:  "50-55" * 56:  "56+"
            String age_index = "0";
            if (age != 0) {
                if (age < 18) 
                    age_index = "1";
                else if (age < 25)
                    age_index = "18";
                else if (age < 35)
                    age_index = "25";
                else if (age < 45)
                    age_index = "35";
                else if (age < 50)
                    age_index = "45";
                else if (age < 56)
                    age_index = "50";
                else
                    age_index = "56";
            }
            boolean check = true;
            while (check) {
                // users.dat 파일에서 (gender:age:occup)에 해당하는 유저 userIdList에 추가
                String userline = "";
                ArrayList<String> userIdList = new ArrayList<String>();
                while((userline = userbuf.readLine()) != null){
                    String[] userwords = userline.split("::");
                    String userId = userwords[0];
                    String userGender = userwords[1];
                    String userAge = userwords[2];
                    int userOccup = Integer.parseInt(userwords[3]);

                    if (occup_list[userOccup].contains("/")) {
                        String[] occups = occup_list[userOccup].split("/");
                        for (String s: occups) {
                            if ((s.equalsIgnoreCase(occupation) || occup_null) && (userGender.equalsIgnoreCase(gender) || gender_null) && (userAge.equals(age_index) || age_null)){
                                userIdList.add(userId);
                                break;
                            }
                        }
                    } else {
                        if ((occup_list[userOccup].equalsIgnoreCase(occupation) || occup_null) && (userGender.equalsIgnoreCase(gender) || gender_null) && (userAge.equals(age_index) || age_null))
                            userIdList.add(userId);
                    }
                }

                // System.out.println("user length: " + userIdList.size());

                // ratings.dat 파일에서 userIdList에 있는 user들의 rating을 MovieGroup안에 (movieId, rating_sum, ratingNum, rating_avg, flag) 형태로 저장
                double [][] movieGroup = new double[4000][5];
                String ratingline = "";
                int count = 0;
                int ratecount = 0;
                while((ratingline = ratingbuf.readLine()) != null){
                    String[] ratingwords = ratingline.split("::");
                    String userId = ratingwords[0];
                    Integer movieId = Integer.parseInt(ratingwords[1]);
                    Integer rating = Integer.parseInt(ratingwords[2]);
                    for (String ids: userIdList) {
                        if (ids.equals(userId)) {
                            if (count == 0 && movieId != titleId) {//w
                                movieGroup[0][0] = movieId;
                                movieGroup[0][1] = rating;
                                movieGroup[0][2] = 1;
                                movieGroup[0][3] = movieGroup[0][1];
                                movieGroup[0][4] = 0;
                                count++;
                                ratecount++;
                                break;
                            } else {
                                for(int i=0 ; i < count ; i++) {
                                    if ((int) movieGroup[i][0] == movieId && movieId != titleId) { // movieGroup안에 movieId와 같은 영화가 이미 있다면 //w
                                        movieGroup[i][1] += rating;
                                        movieGroup[i][2] += 1;
                                        movieGroup[i][3] = movieGroup[i][1] / movieGroup[i][2];
                                        movieGroup[i][4] = 0;
                                        ratecount++;
                                        break;
                                    }
                                    else if (i == (count-1) && movieId != titleId){// movieGroup 안에 movie ID와 같은 영화가 없을때//w
                                        movieGroup[count][0] = movieId;
                                        movieGroup[count][1] = rating;
                                        movieGroup[count][2] = 1;
                                        movieGroup[count][3] = movieGroup[count][1] / movieGroup[count][2];
                                        movieGroup[count][4] = 0;
                                        ratecount++;
                                        count++;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                // System.out.println("count: " + count);
                // System.out.println("rate: " + ratecount);

                // movieGroup의 movie 개수가 10개 이상이 아닐때 - occupation 을 뺴고 다시 검색
                if (limit_null) {
                    if (count < limit) {
                        occupation = "";
                        occup_null = true;
                        ratingbuf = new BufferedReader(new FileReader(ratingfile));
                        userbuf = new BufferedReader(new FileReader(userfile));
                        continue;
                    }
                }
                check = false;
                
                // movieGroup을 rating_avg의 내림차순으로 정렬 -> rating_avg 같다면 rating_num 순으로 정렬
                Arrays.sort(movieGroup, new Comparator<double[]>(){
                    public int compare(double[] m1, double[] m2) {
                        if (m1[3] == m2[3]) {
                            return Double.compare(m2[2], m1[2]);
                        }
                        else {
                            return Double.compare(m2[3], m1[3]);
                        }
                    }
                });

                String movieline = "";
                int index = 0;
                // movie_genre가 인자로 들어올 때
                if (!genre_null) {
                    // movie_genre가 |를 포함할 때
                    if (genre.contains("|")) {
                        for (int i = 0; i < count; i++) {
                            movieline = "";
                            if (index >= limit)
                                break;
                            if (limit_null) {
                                if (movieGroup[i][2] > ratecount/count) 
                                    continue;
                            }
                            while((movieline = moviebuf.readLine()) != null){
                                boolean flag = false;
                                boolean flag2 = false;
                                String[] moviewords = movieline.split("::");
                                Integer movieId = Integer.parseInt(moviewords[0]);
                                String movieName = moviewords[1];
                                String moviegenre = moviewords[2];
                                String[] genres_list = moviegenre.split("\\|"); // 특수문자 | 는 앞에 \\가 붙어야 구분가능
                                String[] input_genre_list = genre.split("\\|");
                                for (String input: input_genre_list) {
                                    for (String s: genres_list) {
                                        if (input.equalsIgnoreCase(s)) {
                                            if (movieId == (int)movieGroup[i][0]) {
                                                movie_list[index][0] = Integer.toString(movieId);
                                                movie_list[index][1] = movieName;
                                                movie_list[index][2] = moviegenre;
                                                movieGroup[i][4] = 1;
                                                index++;
                                                flag = true;
                                                flag2 = true;
                                                break;
                                                
                                            } 
                                        }
                                    }
                                    if (flag)
                                        break;
                                }
                                if (flag2)
                                    break;
                            }
                            moviebuf = new BufferedReader(new FileReader(moviefile));

                            if (i == (count -1)) {
                                if (index < limit) {
                                    // System.out.println("First index: " + index);
                                    for (int j = 0; j < count; j++) {
                                        movieline = "";
                                        if (index >= limit)
                                            break;
                                        if ((int) movieGroup[j][4] != 0)
                                            continue;
                                        if (limit_null) {
                                            if (movieGroup[i][2] < ratecount/count)
                                                continue;
                                        }
                                        while((movieline = moviebuf.readLine()) != null) {
                                            boolean flag = false;
                                            String[] moviewords = movieline.split("::");
                                            Integer movieId = Integer.parseInt(moviewords[0]);
                                            String movieName = moviewords[1];
                                            String moviegenre = moviewords[2];
                                            String[] genres_list = moviegenre.split("\\|"); // 특수문자 | 는 앞에 \\가 붙어야 구분가능
                                            // movie_list 에 포함되지 않은 영화들만 movieGroup 중에서 오름차순으로 movie_list에 넣어줌.
                                            if (movieId == (int)movieGroup[j][0]) {
                                                movie_list[index][0] = Integer.toString(movieId);
                                                movie_list[index][1] = movieName;
                                                movie_list[index][2] = moviegenre;
                                                movieGroup[j][4] = 1;
                                                index++;
                                                flag = true;
                                                break;
                                            } 
                                            if (flag)
                                                break;
                                        }
                                        moviebuf = new BufferedReader(new FileReader(moviefile));
                                    }
                                }
                            }
                        }
                    } else { // movie_genre가 |를 포함하지 않을 때
                        for (int i = 0; i < count; i++) {
                            movieline = "";
                            if (index >= limit)
                                break;
                            if (limit_null) {
                                if (movieGroup[i][2] < ratecount/count)
                                    continue;
                            }
                            while((movieline = moviebuf.readLine()) != null){
                                boolean flag = false;
                                String[] moviewords = movieline.split("::");
                                Integer movieId = Integer.parseInt(moviewords[0]);
                                String movieName = moviewords[1];
                                String moviegenre = moviewords[2];
                                String[] genres_list = moviegenre.split("\\|"); // 특수문자 | 는 앞에 \\가 붙어야 구분가능
                                for (String s: genres_list) {
                                    if (genre.equalsIgnoreCase(s)) {
                                        if (movieId == (int)movieGroup[i][0]) {
                                            movie_list[index][0] = Integer.toString(movieId);
                                            movie_list[index][1] = movieName;
                                            movie_list[index][2] = moviegenre;
                                            movieGroup[i][4] = 1;
                                            index++;
                                            flag = true;
                                            break;
                                        }
                                    }
                                }
                                if (flag)
                                    break;
                            }
                            moviebuf = new BufferedReader(new FileReader(moviefile));

                            if (i == (count -1)) {
                                if (index < limit) {
                                    // System.out.println("First index: " + index);
                                    for (int j = 0; j < count; j++) {
                                        movieline = "";
                                        if (index >= limit)
                                            break;
                                        if (movieGroup[j][2] < ratecount/count)
                                            continue;
                                        if ((int) movieGroup[j][4] != 0)
                                            continue;
                                        while((movieline = moviebuf.readLine()) != null) {
                                            boolean flag = false;
                                            String[] moviewords = movieline.split("::");
                                            Integer movieId = Integer.parseInt(moviewords[0]);
                                            String movieName = moviewords[1];
                                            String moviegenre = moviewords[2];
                                            // movie_list 에 포함되지 않은 영화들만 movieGroup 중에서 오름차순으로 movie_list에 넣어줌.
                                            if (movieId == (int)movieGroup[j][0]) {
                                                movie_list[index][0] = Integer.toString(movieId);
                                                movie_list[index][1] = movieName;
                                                movie_list[index][2] = moviegenre;
                                                movieGroup[j][4] = 1;
                                                index++;
                                                flag = true;
                                                break;
                                            } 
                                            if (flag)
                                                break;
                                        }
                                        moviebuf = new BufferedReader(new FileReader(moviefile));
                                    }
                                }
                            }
                        }
                    }
                } else { // movie_genre 가 인자로 들어오지 않았고, 들어왔어도 "" 일떄
                    for (int i = 0; i < count ; i++) {
                        movieline = "";
                        if (index >= limit)
                            break;
                        if (limit_null) {
                            if (movieGroup[i][2] < ratecount/count)
                                continue;
                        }
                        while((movieline = moviebuf.readLine()) != null){
                            boolean flag = false;
                            String[] moviewords = movieline.split("::");
                            Integer movieId = Integer.parseInt(moviewords[0]);
                            String movieName = moviewords[1];
                            String moviegenre = moviewords[2];
                            if (movieId == (int)movieGroup[i][0]) {
                                movie_list[index][0] = Integer.toString(movieId);
                                movie_list[index][1] = movieName;
                                movie_list[index][2] = moviegenre;
                                index++;
                                flag = true;
                                break;
                            }
                            if (flag)
                                break;
                        }
                        moviebuf = new BufferedReader(new FileReader(moviefile));
                    } 
                }

                String linkline = "";
                while((linkline = linkbuf.readLine()) != null){
                    String[] linkwords = linkline.split("::");
                    Integer movieId = Integer.parseInt(linkwords[0]);
                    String imdbId = linkwords[1];
                    for (int i=0; i< limit; i++) {
                        if (movieId == Integer.parseInt(movie_list[i][0])) {
                            link_list[i] = imdbId;
                            break;
                        }
                    }    
                }
            }

            for (int i=0; i<limit; i++) {
                Movie movie = new Movie(movie_list[i][1], movie_list[i][2], "http://www.imdb.com/title/tt" + link_list[i]);
                result.add(movie);
            }

            moviebuf.close();
            ratingbuf.close();
            userbuf.close();
            linkbuf.close();

        } catch(IOException e){
            System.out.println(e);
        }
    }

}