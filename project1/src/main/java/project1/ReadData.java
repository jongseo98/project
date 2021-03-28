package project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class ReadData {

    public static final String[] occup_list = new String[]{
        "other","academic/educator", "artist", "clerical/admin", "college/grad student", "customer service", 
        "doctor/health care", "executive/managerial", "farmer", "homemaker", "K-12 student", "lawyer", 
        "programmer", "retired", "sales/marketing", "scientist", "self-employed", "technician/engineer", 
        "tradesman/craftsman", "unemployed", "writer"
    };

    // public String OccupToNum(String occup) {
    //     String num="";
    //     if ()
    //     return num;
    // }
    public static void main(String[] args){
        try{
            // Scanner로 input 값 입력 받기
            // Scanner scanner = new Scanner(System.in);
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
                for (String s: genres) {
                    if (movie_input.equalsIgnoreCase(s) || movie_input.equalsIgnoreCase(moviegenre)) { // 대소문자 상관없이 input 값과 비교
                        // System.out.println(movieline);
                        String movieId = moviewords[0];
                        movieIds.add(movieId);
                        break;
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
                occup_input = occup_input.toLowerCase();
                if (occup_list[userOccup].contains(occup_input)) {
                    // System.out.println("userId: " + userId);
                    userIds.add(userId);
                }
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

            System.out.println("rating_sum: " + rating_sum); 
            System.out.println("num: " + rating_num); 
            double rating_avg = rating_sum/rating_num;
            System.out.println("rating: " + rating_avg);    

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
