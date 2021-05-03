package project2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
// import java.util.Scanner;
// import java.util.Arrays;

public class Recommend {

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

    // gender:age:occup => user.dat 파일에서 일치하는 user 를 userID list에 추가
    // rating.dat 파일에서 해당 user가 쓴 평점을 통해 movieGroup에 (movieId, rating_sum, rating_num, rating_avg) 추가
    // movieGroup을 rating_avg 순으로 정렬, 같다면 rating_num 순으로 내림차순 정렬 
    // 만약 genre도 input 있을때, movieGroup 안에 있는 영화중 해당 장르에 속한 영화
    // genre input 없을때,  movieGroup 안에 있는 영화중 rating_num > count/rating_count 를 넘은 영화를 추출해서 movie_list 에 (movieId, movieName)으로 저장
    // links.dat 파일에서 movie_list에 해당하는 영화의 imdbID를 link_list에 추출
    // movie_list 와 link_list를 10개 출력
    public static void main(String[] args){
        try{
            if (args.length < 3) {
                System.out.println("Error. The number of input ​​should be at least 3.");
                return;
            }

            String gender_input = args[0];
            int age_input = 0;
            if (!args[1].isEmpty())
                age_input = Integer.parseInt(args[1]);
            String occup_input = args[2];
            String genre_input = "";
            if (args.length > 3)
                genre_input = args[3];
            
            boolean gender_null = false;
            boolean age_null = false;
            boolean occup_null = false;
            boolean genre_null = false;
            
            if (args[0].isEmpty())
                gender_null = true;
            if (args[1].isEmpty())
                age_null = true;
            if (args[2].isEmpty())
                occup_null = true;
            if (args.length > 3) {
                if (args[3].isEmpty())
                    genre_null = true;
            }

            // gender_input 이 비정상일때
            if (!gender_null) {
                if (!(gender_input.equalsIgnoreCase("F")) && !(gender_input.equalsIgnoreCase("M"))) {
                    System.out.println("Error. User gender is F or M");
                    return;
                }
            }

            // age_input 이 비정상일때
            if (!age_null) {
                if (age_input <= 0) {
                    System.out.println("Error. User age cannot be under 0");
                    return;
                }
            }

            // occup_input이 occup_list에 없는 경우
            if (!occup_null) {
                boolean occup_bool = false;
                for (String occup: occup_list) {
                    if (occup.contains("/")) {
                        String[] occups = occup.split("/");
                        for (String s: occups) {
                            if (occup_input.equalsIgnoreCase(s)) {
                                occup_bool = true;
                                break;
                            }
                        }
                    } else {
                        if (occup_input.equalsIgnoreCase(occup))
                            occup_bool = true;
                    }
                    if (occup_bool)
                        break;
                }
                if (!occup_bool) {
                    System.out.println("Error. The occupation does not exist");
                    return;
                }
            }

            // genre_input이 genre_list에 없는 경우
            if (args.length > 3) {
                if (!genre_null) {
                    boolean genre_bool = false;
                    if (genre_input.contains("|")) {
                        String[] input_genre_list = genre_input.split("\\|");
                        for (String input: input_genre_list) {
                            genre_bool = false;
                            for (String genre: movie_genre_list) {
                                if (input.equalsIgnoreCase(genre)) {
                                    genre_bool = true;
                                    break;
                                }
                            }
                        }
                    } else {
                        for (String genre: movie_genre_list) {
                            if (genre_input.equalsIgnoreCase(genre)) {
                                genre_bool = true;
                                break;
                            }
                        }
                    }
                    if (!genre_bool) {
                        System.out.println("Error. The movie genre does not exist");
                        return;
                    }
                }
            }

            //파일 객체 생성
            File moviefile = new File("/root/project/project2/data/ml-1m/movies.dat");
            File ratingfile = new File("/root/project/project2/data/ml-1m/ratings.dat");
            File userfile = new File("/root/project/project2/data/ml-1m/users.dat");
            File linkfile = new File("/root/project/project2/data/ml-1m/links.dat");
            //입력 버퍼 생성
            BufferedReader moviebuf = new BufferedReader(new FileReader(moviefile));
            BufferedReader ratingbuf = new BufferedReader(new FileReader(ratingfile));
            BufferedReader userbuf = new BufferedReader(new FileReader(userfile));
            BufferedReader linkbuf = new BufferedReader(new FileReader(linkfile));

            // *  1:  "Under 18" * 18:  "18-24" * 25:  "25-34" * 35:  "35-44" * 45:  "45-49" * 50:  "50-55" * 56:  "56+"
            String age_index = "0";
            if (!args[1].isEmpty()) {
                if (age_input < 18) 
                    age_index = "1";
                else if (age_input < 25)
                    age_index = "18";
                else if (age_input < 35)
                    age_index = "25";
                else if (age_input < 45)
                    age_index = "35";
                else if (age_input < 50)
                    age_index = "45";
                else if (age_input < 56)
                    age_index = "50";
                else
                    age_index = "56";
            }
            boolean check10 = true;
            while (check10) {
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
                        for (String s: occups)
                            if ((s.equalsIgnoreCase(occup_input) || occup_null) && (userGender.equalsIgnoreCase(gender_input) || gender_null) && (userAge.equals(age_index) || age_null)){
                                userIdList.add(userId);
                                break;
                            }
                    } else {
                        if ((occup_list[userOccup].equalsIgnoreCase(occup_input) || occup_null) && (userGender.equalsIgnoreCase(gender_input) || gender_null) && (userAge.equals(age_index) || age_null))
                            userIdList.add(userId);
                    }
                }


                

                // ratings.dat 파일에서 userIdList에 있는 user들의 rating을 MovieGroup안에 (movieId, rating_sum, ratingNum, rating_avg) 형태로 저장
                double [][] movieGroup = new double[4000][4];
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
                            if (count == 0) {
                                movieGroup[0][0] = movieId;
                                movieGroup[0][1] = rating;
                                movieGroup[0][2] = 1;
                                movieGroup[0][3] = movieGroup[0][1];
                                count++;
                                ratecount++;
                                break;
                            } else {
                                for(int i=0 ; i < count ; i++) {
                                    if (movieGroup[i][0] == movieId) { // movieGroup안에 movieId와 같은 영화가 이미 있다면
                                        movieGroup[i][1] += rating;
                                        movieGroup[i][2] += 1;
                                        movieGroup[i][3] = movieGroup[i][1] / movieGroup[i][2];
                                        ratecount++;
                                        break;
                                    }
                                    else if (i == (count-1)){// movieGroup 안에 movie ID와 같은 영화가 없을때
                                        movieGroup[count][0] = movieId;
                                        movieGroup[count][1] = rating;
                                        movieGroup[count][2] = 1;
                                        movieGroup[count][3] = movieGroup[count][1] / movieGroup[count][2];
                                        ratecount++;
                                        count++;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                // movieGroup의 movie 개수가 10개 이상이 아닐때 - occup_input 을 뺴고 다시 검색
                if (count < 10) {
                    occup_input = "";
                    occup_null = true;
                    ratingbuf = new BufferedReader(new FileReader(ratingfile));
                    userbuf = new BufferedReader(new FileReader(userfile));
                    continue;
                }
                check10 = false;
                
                // movieGroup을 rating_avg의 내림차순으로 정렬 -> rating_avg 같다면 rating_num 순으로 정렬
                Arrays.sort(movieGroup, new Comparator<double[]>(){
                    public int compare(double[] m1, double[] m2) {
                        if (m1[3] == m2[3]) {
                            return Double.compare(m2[2], m1[2]);
                        }
                        else
                            return Double.compare(m2[3], m1[3]);
                    }
                });

                String movieline = "";
                String [][] movie_list = new String[10][2];
                int index = 0;
                // movie_genre가 인자로 들어올 때
                if (args.length > 3 && !genre_null) {
                    // movie_genre가 |를 포함할 때
                    if (genre_input.contains("|")) {
                        for (int i = 0; i < count; i++) {
                            movieline = "";
                            if (index >= 10)
                                break;
                            while((movieline = moviebuf.readLine()) != null){
                                boolean flag = false;
                                boolean flag2 = false;
                                String[] moviewords = movieline.split("::");
                                Integer movieId = Integer.parseInt(moviewords[0]);
                                String movieName = moviewords[1];
                                String moviegenre = moviewords[2];
                                String[] genres = moviegenre.split("\\|"); // 특수문자 | 는 앞에 \\가 붙어야 구분가능
                                String[] input_genre_list = genre_input.split("\\|");
                                for (String input: input_genre_list) {
                                    for (String s: genres) {
                                        if (input.equalsIgnoreCase(s)) {
                                            if (movieId == (int)movieGroup[i][0]) {
                                                if (movieGroup[i][2] > ratecount/count) {
                                                    movie_list[index][0] = Integer.toString(movieId);
                                                    movie_list[index][1] = movieName;
                                                    index++;
                                                    flag = true;
                                                    flag2 = true;
                                                    break;
                                                }
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
                        }
                    } else { // movie_genre가 |를 포함하지 않을 때
                        for (int i = 0; i < count; i++) {
                            movieline = "";
                            if (index >= 10)
                                break;
                            while((movieline = moviebuf.readLine()) != null){
                                boolean flag = false;
                                String[] moviewords = movieline.split("::");
                                Integer movieId = Integer.parseInt(moviewords[0]);
                                String movieName = moviewords[1];
                                String moviegenre = moviewords[2];
                                String[] genres = moviegenre.split("\\|"); // 특수문자 | 는 앞에 \\가 붙어야 구분가능
                                for (String s: genres) {
                                    if (genre_input.equalsIgnoreCase(s)) {
                                        if (movieId == (int)movieGroup[i][0]) {
                                            if (movieGroup[i][2] > ratecount/count) {
                                                movie_list[index][0] = Integer.toString(movieId);
                                                movie_list[index][1] = movieName;
                                                index++;
                                                flag = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (flag)
                                    break;
                            }
                            moviebuf = new BufferedReader(new FileReader(moviefile));
                        }
                    }            
                } else { // movie_genre 가 인자로 들어오지 않았고, 들어왔어도 "" 일떄
                    for (int i = 0; i < count ; i++) {
                        if (index >= 10)
                            break;
                        if (movieGroup[i][2] > ratecount/count) {
                            int movieId = (int) movieGroup[i][0];
                            movie_list[index][0] = Integer.toString(movieId);
                            index++;
                        }
                    }
                    while((movieline = moviebuf.readLine()) != null){
                        String[] moviewords = movieline.split("::");
                        Integer movieId = Integer.parseInt(moviewords[0]);
                        String movieName = moviewords[1];
                        for (int i=0; i<10; i++) {
                            if (movieId == Integer.parseInt(movie_list[i][0])) {
                                movie_list[i][1] = movieName;
                                break;
                            }
                        }
                    } 
                }

                String linkline = "";
                String [] link_list = new String[10];
                while((linkline = linkbuf.readLine()) != null){
                    String[] linkwords = linkline.split("::");
                    Integer movieId = Integer.parseInt(linkwords[0]);
                    String imdbId = linkwords[1];
                    for (int i=0; i< 10; i++) {
                        if (movieId == Integer.parseInt(movie_list[i][0])) {
                            link_list[i] = imdbId;
                            break;
                        }
                    }    
                }

                for (int i=0; i<10; i++)
                    System.out.println(movie_list[i][1] + " http://www.imdb.com/title/tt" + link_list[i]); 
            }

            moviebuf.close();
            ratingbuf.close();
            userbuf.close();
            linkbuf.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
        } catch(IOException e){
            System.out.println(e);
        }
    }
}