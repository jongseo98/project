package project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadData {

    public static final String[] occup_list = new String[]{
        "other","academic/educator", "artist", "clerical/admin", "college/gradstudent", "customerservice", 
        "doctor/healthcare", "executive/managerial", "farmer", "homemaker", "K-12student", "lawyer", 
        "programmer", "retired", "sales/marketing", "scientist", "self-employed", "technician/engineer", 
        "tradesman/craftsman", "unemployed", "writer"
    };

    public static void main(String[] args){
        try{
            String movie_input = args[0];
            String occup_input = args[1];

            // System.out.println("movie: " + movie_input + " occup: " + occup_input);
            // String input = input.replace(" ", ""); // Input 공백 제거

            //파일 객체 생성
            File moviefile = new File("/root/project/project1/data/ml-1m/movies.dat");
            File ratingfile = new File("/root/project/project1/data/ml-1m/ratings.dat");
            File userfile = new File("/root/project/project1/data/ml-1m/users.dat");
            //입력 버퍼 생성
            BufferedReader moviebuf = new BufferedReader(new FileReader(moviefile));
            BufferedReader ratingbuf = new BufferedReader(new FileReader(ratingfile));
            BufferedReader userbuf = new BufferedReader(new FileReader(userfile));

            // MovieID::Title::Genres - movie.dat
            // movie 파일 읽기
            String movieline = "";
            ArrayList<String> movieIds = new ArrayList<String>();

            while((movieline = moviebuf.readLine()) != null){
                String[] moviewords = movieline.split("::");
                String moviegenre = moviewords[2];
                String[] genres = moviegenre.split("\\|"); // 특수문자 | 는 앞에 \\가 붙어야 구분가능

                if (movie_input.contains("|")) {
                    String[] movie_inputs = movie_input.split("\\|");
                    boolean flag = false;
                    for (String input: movie_inputs) {
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
                    for (String s: genres) {
                        if (movie_input.equalsIgnoreCase(s)) { // 대소문자 상관없이 input 값과 비교
                            String movieId = moviewords[0];
                            movieIds.add(movieId);
                            break;
                        }
                    }
                }
            }

            // UserID::Gender::Age::Occupation::Zip-code - user.dat
            // *  1:  "academic/educator"
	        // *  2:  "artist"
            String userline = "";
            ArrayList<String> userIds = new ArrayList<String>();

            while((userline = userbuf.readLine()) != null){
                String[] userwords = userline.split("::");
                String userId = userwords[0];
                int userOccup = Integer.parseInt(userwords[3]);
                // occup_input = occup_input.toLowerCase();
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

            // UserID::MovieID::Rating::Timestamp - rating.dat
            double rating_sum = 0;
            double rating_num = 0;

            String ratingline = "";
            while((ratingline = ratingbuf.readLine()) != null){
                String[] ratingwords = ratingline.split("::");
                if (userIds.contains(ratingwords[0]) && movieIds.contains(ratingwords[1])) {
                    // System.out.println("userId: " + ratingwords[0] + " movieId: "+ ratingwords[1] + " rating: " + ratingwords[2]);
                    rating_sum += Double.parseDouble(ratingwords[2]);
                    rating_num += 1;
                }
            }

            double rating_avg = rating_sum/rating_num;
            if (rating_sum == 0 || rating_num == 0)
                System.out.println("Error. This inputs can't be aceepted. Try again");
            else  
                System.out.println(rating_avg);    

            //.readmovieline()은 끝에 개행문자를 읽지 않는다.            
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
