package project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadData {

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

    public static void main(String[] args){
        try{
            String movie_input = args[0];
            String occup_input = args[1];

            //파일 객체 생성
            File moviefile = new File("/root/project/project1/data/ml-1m/movies.dat");
            File ratingfile = new File("/root/project/project1/data/ml-1m/ratings.dat");
            File userfile = new File("/root/project/project1/data/ml-1m/users.dat");
            //입력 버퍼 생성
            BufferedReader moviebuf = new BufferedReader(new FileReader(moviefile));
            BufferedReader ratingbuf = new BufferedReader(new FileReader(ratingfile));
            BufferedReader userbuf = new BufferedReader(new FileReader(userfile));

            String movieline = "";
            ArrayList<String> movieIds = new ArrayList<String>();

            boolean exist_genre = false; // input 값의 movie 장르가 없는경우

            while((movieline = moviebuf.readLine()) != null){
                String[] moviewords = movieline.split("::");
                String moviegenre = moviewords[2];
                String[] genres = moviegenre.split("\\|"); // 특수문자 | 는 앞에 \\가 붙어야 구분가능

                if (movie_input.contains("|")) {
                    String[] movie_inputs = movie_input.split("\\|");
                    boolean flag = false;
                    for (String input: movie_inputs) {
                        if (Arrays.asList(movie_genre_list).contains(input))
                            exist_genre = true;
                        else {
                            System.out.println("Error. This input can't be aceepted. Try again");
                            return;
                        }
                        flag = false;
                        for (String s: genres) {
                            if (input.equalsIgnoreCase(s)) { 
                                flag = true;
                                break;
                            }
                        }
                        if (!flag)
                            break;
                    }
                    if (flag) {
                        String movieId = moviewords[0];
                        movieIds.add(movieId);
                    }
                } else {
                    if (Arrays.asList(movie_genre_list).contains(movie_input))
                            exist_genre = true;
                    else {
                        System.out.println("Error. This input can't be aceepted. Try again");
                        return;
                    }
                    for (String s: genres) {
                        if (movie_input.equalsIgnoreCase(s)) { // 대소문자 상관없이 input 값과 비교
                            String movieId = moviewords[0];
                            movieIds.add(movieId);
                            break;
                        }
                    }
                }
            }

            String userline = "";
            ArrayList<String> userIds = new ArrayList<String>();

            while((userline = userbuf.readLine()) != null){
                String[] userwords = userline.split("::");
                String userId = userwords[0];
                int userOccup = Integer.parseInt(userwords[3]);
                if (occup_list[userOccup].contains("/")) {
                    String[] occups = occup_list[userOccup].split("/");
                    for (String s: occups) {
                        if (s.equalsIgnoreCase(occup_input))
                            userIds.add(userId);
                    }
                } else {
                    if (occup_list[userOccup].equalsIgnoreCase(occup_input))
                        userIds.add(userId);
                }

                // userIds.add(userId);
                if (occup_list[userOccup].equals(occup_input))
                    userIds.add(userId);
            }

            double rating_sum = 0;
            double rating_num = 0;

            String ratingline = "";
            while((ratingline = ratingbuf.readLine()) != null){
                String[] ratingwords = ratingline.split("::");
                if (userIds.contains(ratingwords[0]) && movieIds.contains(ratingwords[1])) {
                    rating_sum += Double.parseDouble(ratingwords[2]);
                    rating_num += 1;
                }
            }

            double rating_avg = rating_sum/rating_num;
            if (rating_num == 0.0 && exist_genre)
                System.out.println(0);
            else  
                System.out.println(rating_avg);
            
            moviebuf.close();
            ratingbuf.close();
            userbuf.close();
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
    }
}