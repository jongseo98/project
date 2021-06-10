package project.project4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import project.project4.link.LinkRepository;
import project.project4.link.Link;
import project.project4.movie.MovieRepository;
import project.project4.movie.Movie;
import project.project4.movie_poster.MoviePosterRepository;
import project.project4.movie_poster.MoviePoster;
import project.project4.rating.RatingRepository;
import project.project4.rating.Rating;
import project.project4.user.UserRepository;
import project.project4.user.User;
import project.project4.result.Result;
import project.project4.result.ResultRepository;


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

    private List<Result> resultarray = new ArrayList<>();
    public List<Result> getResultarray() {
        return resultarray;
    }

    private final LinkRepository linkRepository;
	private final MovieRepository movieRepository;
	private final MoviePosterRepository movieposterRepository;
	private final RatingRepository ratingRepository;
	private final UserRepository userRepository;
    private final ResultRepository resultRepository;

    public Recommendation(String title, int limit, LinkRepository linkRepository, MovieRepository movieRepository, MoviePosterRepository movieposterRepository, 
		RatingRepository ratingRepository, UserRepository userRepository, ResultRepository resultRepository) {
        this.title = title;
        this.limit = limit;
        this.gender = "";
        this.age = 0;
        this.occupation = "";
        this.genre = "";
        this.err = false;
        this.err_msg = "";

        this.linkRepository = linkRepository;
		this.movieRepository = movieRepository;
		this.movieposterRepository = movieposterRepository;
		this.ratingRepository = ratingRepository;
		this.userRepository = userRepository;
        this.resultRepository = resultRepository;
    }

    public Recommendation(String gender, String age, String occupation, String genre, LinkRepository linkRepository, MovieRepository movieRepository, 
        MoviePosterRepository movieposterRepository, RatingRepository ratingRepository, UserRepository userRepository, ResultRepository resultRepository) {
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

        this.linkRepository = linkRepository;
		this.movieRepository = movieRepository;
		this.movieposterRepository = movieposterRepository;
		this.ratingRepository = ratingRepository;
		this.userRepository = userRepository;
        this.resultRepository = resultRepository;
    }

    @Override
	public String toString() {
        String s = "";
        if (err) {
            s += err_msg;
        } else {
            s += "[{\n";
            for (int i=0; i<limit; i++) {
                Result result= resultarray.remove(0);
                s += "  \"title\": " + "\"" + result.getTitle() + "\"" + ",\n";
                s += "  \"genre\": " + "\"" + result.getGenre() + "\"" + ",\n";
                s += "  \"imdb\": " + "\""+ result.getImdb() + "\"" + ",\n";
                
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

    public String getOccupIndex() {
        String idx_occup = "";
        boolean occup_bool = false;
        int idx = 0;
        for (String occup: occup_list) {
            if (occup.contains("/")) {
                String[] occups = occup.split("/");
                for (String s: occups) {
                    if (occupation.equalsIgnoreCase(s)) {
                        occup_bool = true;
                        idx_occup =  Integer.toString(idx);
                        break;
                    }
                }
            } else {
                if (occupation.equalsIgnoreCase(occup))
                    occup_bool = true;
                    idx_occup =  Integer.toString(idx);
                }
            if (occup_bool)
                break;
            idx++;
        }
        return idx_occup;
    }

    public String getAgeIdx(int age) {
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
        return age_index;   
    }

    public List<User> findUserList(boolean gender_null, boolean age_null, boolean occup_null) {
        List<User> result;
        String str_age = getAgeIdx(age);
        String idx_occup = getOccupIndex();
        System.out.println("idx_occup: " + idx_occup);
        System.out.println("gender_null: " + gender_null);
        System.out.println("age_null: " + age_null);
        System.out.println("occup_null: " + occup_null);
        if (gender_null && age_null && occup_null) {
            result = userRepository.findAll();
            System.out.println("1");
        } else if (gender_null && age_null) {
            result = userRepository.findByOccupation(idx_occup);
            System.out.println("2");
        } else if (gender_null && occup_null) {
            result = userRepository.findByAge(str_age);
            System.out.println("3");
        } else if (age_null && occup_null) {
            result = userRepository.findByGender(gender);
            System.out.println("4");
        } else if (gender_null) {
            result = userRepository.findByAgeAndOccupation(str_age, idx_occup);
            System.out.println("5");
        } else if (age_null) {
            result = userRepository.findByGenderAndOccupation(gender, idx_occup);
            System.out.println("6");
        } else if (occup_null) {
            result = userRepository.findByGenderAndAge(gender, str_age);
            System.out.println("7");
        }
        else {
            result = userRepository.findByGenderAndAgeAndOccupation(gender, str_age, idx_occup);
            System.out.println("8");
        }
        return result;
    }

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
            // 파일 객체 생성
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
            age_index = getAgeIdx(age);
            boolean check = true;
            while (check) {
                // users.dat 파일에서 (gender:age:occup)에 해당하는 유저 userIdList에 추가
                String userline = "";

                List<User> userList = findUserList(gender_null, age_null, occup_null);
                // for (User u: userList) {
                //     System.out.println("Id: " + u.getId());
                //     System.out.println("gender: " + u.getGender());
                //     System.out.println("age: " + u.getAge());
                //     System.out.println("occupation: " + u.getOccupation());
                //     System.out.println("--------------------------------");
                // }

                double [][] movieGroupTest = new double[4000][6];
                int count1 = 0;
                int ratecount1 = 0;
                double c1=0;
                for (User user: userList) {
                    List<Rating> ratingTest = ratingRepository.findByUserId(user.getId());
                    int ratingSum = 0;
                    int ratingNum = 0;
                    int movieIdTest = 0;
                    for (Rating r: ratingTest) {
                        ratingSum += Integer.parseInt(r.getRating());
                        ratingNum++;
                        ratecount1++;
                        movieIdTest = Integer.parseInt(r.getMovieId());
                    }
                    double ratingAvg = (double)ratingSum/ratingNum;
                    movieGroupTest[count1][0] = movieIdTest;
                    movieGroupTest[count1][1] = ratingSum;
                    movieGroupTest[count1][2] = ratingNum;
                    movieGroupTest[count1][3] = ratingAvg;
                    movieGroupTest[count1][4] = 0;
                    count1++;
                    c1 += ratingAvg;
                    // MovieRating movieRating = new MovieRating(movieIdTest, Integer.toString(ratingSum), Integer.toString(ratingNum), Double.toString(ratingAvg));
                    // System.out.println("movieId: " + movieIdTest);
                    // System.out.println("ratingSum: " + ratingSum);
                    // System.out.println("ratingNum: " + ratingNum);
                    // System.out.println("ratingAvg: " + ratingAvg);
                    // movieRatingList.add(movieRating);
                    // movieRatingRepository.save(movieRating);
                }

                System.out.println("count: " + count1);
                System.out.println("rate: " + ratecount1);
                c1 = c1/count1;
                for (int i=0; i<count1; i++) {
                    movieGroupTest[i][5] = (movieGroupTest[i][1])/(movieGroupTest[i][2]+ratecount1/count1)+(ratecount1*c1)/(count1*(movieGroupTest[i][2]+ratecount1/count1));
                }

                Arrays.sort(movieGroupTest, new Comparator<double[]>(){
                    public int compare(double[] m1, double[] m2) {
                        if (m1[5] == m2[5]) {
                            return Double.compare(m2[3], m1[3]);
                        }
                        else {
                            return Double.compare(m2[5], m1[5]);
                        }
                    }
                });

                // for (int i=0; i <count1; i++) {
                //     // System.out.println("movieId: " + movieGroupTest[i][0]);
                //     // System.out.println("ratingSum: " + movieGroupTest[i][1]);
                //     // System.out.println("ratingNum: " + movieGroupTest[i][2]);
                //     // System.out.println("ratingAvg: " + movieGroupTest[i][3]);
                //     // System.out.println("ratingSort: " + movieGroupTest[i][5]);
                //     // System.out.println("-------------------");
                // }

                // movieGroup의 movie 개수가 10개 이상이 아닐때 - occupation 을 뺴고 다시 검색
                if (limit_null) {
                    if (count1 < limit) {
                        occupation = "";
                        occup_null = true;
                        continue;
                    }
                }
                check = false;

                // (movieId, rating_sum, ratingNum, rating_avg, flag, sort_arg)
                int index1 = 0;
                if (!genre_null) {
                    if (genre.contains("|")) {
                        for (int i = 0; i < count1; i++) {
                            if (index1 >= limit)
                                break;
                            
                            int id = (int)movieGroupTest[i][0];
                            String movieId = Integer.toString(id);
                            Movie movie = movieRepository.findById(movieId).get();
                            // String genres = movie.getGenre();
                            String []genres_list = movie.getGenre().split("\\|");
                            String[] input_genre_list = genre.split("\\|");
                            boolean flag = false;
                            for (String input: input_genre_list) {
                                for (String s: genres_list) {
                                    if (input.equalsIgnoreCase(s)) {
                                        movieGroupTest[i][4] = 1;
                                        Result result = new Result();
                                        result.setTitle(movie.getTitle());
                                        result.setGenre(movie.getGenre());
                                        Link link = linkRepository.findById(movieId).get();
                                        result.setImdb("http://www.imdb.com/title/tt" + link.getImdbId());
                                        resultRepository.save(result);
                                        index1++;
                                        flag = true;
                                        break; 
                                    }
                                }
                                if (flag)
                                    break;
                            }

                            if (i == (count1 -1)) {
                                if (index1 < limit) {
                                    // System.out.println("First index: " + index);
                                    for (int j = 0; j < count1; j++) {
                                        if (index1 >= limit)
                                            break;
                                        if ((int) movieGroupTest[j][4] != 0)
                                            continue;
                                            
                                        int id2 = (int)movieGroupTest[j][0];
                                        String movieId2 = Integer.toString(id2);
                                        Movie movie2 = movieRepository.findById(movieId2).get();
                                        movieGroupTest[j][4] = 1;
                                        Result result = new Result();
                                        result.setTitle(movie2.getTitle());
                                        result.setGenre(movie2.getGenre());
                                        Link link = linkRepository.findById(movieId).get();
                                        result.setImdb("http://www.imdb.com/title/tt" + link.getImdbId());
                                        resultRepository.save(result);;
                                        index1++;
                                    }
                                }
                            }
                        }
                        List<Result> resultList = resultRepository.findAll();
                        for (Result r: resultList) {
                            System.out.println("title: " + r.getTitle());
                            System.out.println("gerne: " + r.getGenre());
                            System.out.println("imdb: " + r.getImdb());
                            System.out.println("-----------");
                        }

                        

                    } else {
                        System.out.println("A");
                        for (int i = 0; i < count1; i++) {
                            if (index1 >= limit)
                                break;
                            
                            int id = (int)movieGroupTest[i][0];
                            String movieId = Integer.toString(id);
                            Movie movie = movieRepository.findById(movieId).get();
                            String [] genres_list = movie.getGenre().split("\\|");
                            for (String s: genres_list) {
                                if (genre.equalsIgnoreCase(s)) {
                                    movieGroupTest[i][4] = 1;
                                    Result result = new Result();
                                    result.setTitle(movie.getTitle());
                                    result.setGenre(movie.getGenre());
                                    Link link = linkRepository.findById(movieId).get();
                                    result.setImdb("http://www.imdb.com/title/tt" + link.getImdbId());
                                    resultRepository.save(result);
                                    index1++;
                                    break; 
                                }
                            }
                            if (i == (count1 -1)) {
                                if (index1 < limit) {
                                    // System.out.println("index1: " + index1);
                                    for (int j = 0; j < count1; j++) {
                                        if (index1 >= limit)
                                            break;
                                        if ((int) movieGroupTest[j][4] != 0)
                                            continue;
                                            
                                        int id2 = (int)movieGroupTest[j][0];
                                        String movieId2 = Integer.toString(id2);
                                        Movie movie2 = movieRepository.findById(movieId2).get();
                                        movieGroupTest[j][4] = 1;
                                        Result result = new Result();
                                        result.setTitle(movie2.getTitle());
                                        result.setGenre(movie2.getGenre());
                                        Link link = linkRepository.findById(movieId).get();
                                        result.setImdb("http://www.imdb.com/title/tt" + link.getImdbId());
                                        resultRepository.save(result);
                                        index1++;
                                    }
                                }
                            }
                        }
                        List<Result> resultList = resultRepository.findAll();
                        for (Result r: resultList) {
                            System.out.println("title: " + r.getTitle());
                            System.out.println("gerne: " + r.getGenre());
                            System.out.println("imdb: " + r.getImdb());
                            System.out.println("-----------");
                        }
                    }
                } else {
                    for (int i=0; i < limit; i++) {
                        int id = (int)movieGroupTest[i][0];
                        String movieId = Integer.toString(id);
                        Movie movie = movieRepository.findById(movieId).get();
                        Result result = new Result();
                        result.setTitle(movie.getTitle());
                        result.setGenre(movie.getGenre());
                        Link link = linkRepository.findById(movieId).get();
                        result.setImdb("http://www.imdb.com/title/tt" + link.getImdbId());
                        resultRepository.save(result);
                    }
                    List<Result> resultList = resultRepository.findAll();
                    for (Result r: resultList) {
                        System.out.println("title: " + r.getTitle());
                        System.out.println("gerne: " + r.getGenre());
                        System.out.println("imdb: " + r.getImdb());
                        System.out.println("-----------");
                    }
                }

            }
        } catch(IOException e){
            System.out.println(e);
        }
    }

}