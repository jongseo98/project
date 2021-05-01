package project1;

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

public class Recommend1 {

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
    // rating.dat 파일에서 해당 userID의 movieId 와 rating 추가
    // 각 movieID 별로 rating 비교 후 가장 높은 평점부터 10개 출력
    public static void main(String[] args){
        try{
            // String movie_input = args[0];
            // String occup_input = args[1];
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

            //파일 객체 생성
            File moviefile = new File("/root/project/project1/data/ml-1m/movies.dat");
            File ratingfile = new File("/root/project/project1/data/ml-1m/ratings.dat");
            File userfile = new File("/root/project/project1/data/ml-1m/users.dat");
            File linkfile = new File("/root/project/project1/data/ml-1m/links.dat");
            //입력 버퍼 생성
            BufferedReader moviebuf = new BufferedReader(new FileReader(moviefile));
            BufferedReader ratingbuf = new BufferedReader(new FileReader(ratingfile));
            BufferedReader userbuf = new BufferedReader(new FileReader(userfile));
            BufferedReader linkbuf = new BufferedReader(new FileReader(linkfile));

            // *  1:  "Under 18"
            // * 18:  "18-24"
            // * 25:  "25-34"
            // * 35:  "35-44"
            // * 45:  "45-49"
            // * 50:  "50-55"
            // * 56:  "56+"
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
            // System.out.println("age_index: " + age_index);

            String userline = "";
            ArrayList<String> userIdList = new ArrayList<String>();
            // boolean exist_occup = false;
            while((userline = userbuf.readLine()) != null){
                // System.out.println("XXXXX");
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
                // userIdList.add(userId);
                // if (occup_list[userOccup].equals(occup_input))
                //     userIdList.add(userId);
            }


            // double rating_sum = 0;
            // double rating_num = 0;
            // ArrayList<ArrayList<Integer>> movieGroup = new ArrayList<ArrayList<Integer>>();
            // ArrayList<Integer> movieList = new ArrayList<Integer>();
            double [][] movieGroup = new double[4000][4];
            String ratingline = "";
            int count = 0;
            int ratecount=0;
            while((ratingline = ratingbuf.readLine()) != null){
                String[] ratingwords = ratingline.split("::");
                String userId = ratingwords[0];
                Integer movieId = Integer.parseInt(ratingwords[1]);
                Integer rating = Integer.parseInt(ratingwords[2]);
                for (String ids: userIdList) {
                    if (ids.equals(userId)) {
                        // System.out.println("A")
                        if (count == 0) {
                            movieGroup[0][0] = movieId;
                            movieGroup[0][1] = rating;
                            movieGroup[0][2] = 1;
                            movieGroup[0][3] = movieGroup[0][1];
                            count++;
                            ratecount++;
                            break;
                        } else {
                            // System.out.println("B" + count);
                            for(int i=0 ; i < count ; i++) {
                                if (movieGroup[i][0] == movieId) { // movieGroup안에 movieId와 같은 영화가 이미 있다면
                                    movieGroup[i][1] += rating;
                                    movieGroup[i][2] += 1;
                                    movieGroup[i][3] = movieGroup[i][1] / movieGroup[i][2];
                                    ratecount++;
                                    break;
                                }
                                else if (i == (count-1)){// movieGroup 안에 movie ID와 같은 영화가 없을때
                                     // (movieId, rating_sum, rating_num) 
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

            Arrays.sort(movieGroup, new Comparator<double[]>(){
                public int compare(double[] o1, double[] o2) {
                    return Double.compare(o2[3], o1[3]);
                }
            });

            for(int i=0; i < count; i++)
            {
                for(int j = i+1; movieGroup[i][3]==movieGroup[j][3]; j++)
                {
                    if(movieGroup[i][2] < movieGroup[j][2])
                    {
                        double tempnum = movieGroup[i][2];
                        movieGroup[i][2] = movieGroup[j][2];
                        movieGroup[j][2] = tempnum;
                        
                        double temprat = movieGroup[i][1];
                        movieGroup[i][1] = movieGroup[j][1];
                        movieGroup[j][1] = temprat;
                        
                        double tempID = movieGroup[i][0];
                        movieGroup[i][0] = movieGroup[j][0];
                        movieGroup[j][0] = tempID;
                    }
                }
            }

            String movieline = "";
            // ArrayList<String> movieIds = new ArrayList<String>();
            if (args.length > 3) {
                if(!genre_null)
                {
                    // System.out.println("A");
                    String [][] movie_list = new String[10][2];
                    boolean flag2 = false;
                    int check_index = 0;
                    int count_num = 0;
                    while((movieline = moviebuf.readLine()) != null){
                        String[] moviewords = movieline.split("::");
                        Integer movieId = Integer.parseInt(moviewords[0]);
                        String movieName = moviewords[1];
                        String moviegenre = moviewords[2];
                        String[] genres = moviegenre.split("\\|"); // 특수문자는 앞에 \\가 붙어야 구분가능
                        if (genre_input.contains("|")) {
                            // System.out.println("B");
                            String[] genre_list = genre_input.split("\\|");
                            boolean flag = false;
                            for (String input: genre_list) {
                                flag = false;
                                for (String s: genres) {
                                    if (input.equalsIgnoreCase(s)) {
                                        flag = true;
                                        // System.out.println("C");
                                        break;
                                    }
                                }
                                if (flag)
                                    break;
                            }
                            if (flag) {
                                // System.out.println("D");
                                for (; check_index < count; check_index++) {
                                    // System.out.println("A");
                                    System.out.println((int)movieGroup[check_index][0]);
                                    System.out.println(movieId);
                                    if ((int)movieGroup[check_index][0] == movieId) {
                                        movie_list[count_num][0] = Integer.toString(movieId);
                                        movie_list[count_num][1] = movieName;
                                        count_num++;
                                        System.out.println("count_num: " + count_num);
                                        break;
                                    }
                                    if (count_num >=10) {
                                        flag = true;
                                        break;
                                    }  
                                }
                            }
                            // System.out.println("E");
                        } else {
                            System.out.println("A");
                            boolean flag =false;
                            for (String s: genres) {
                                if (genre_input.equalsIgnoreCase(s)) { // 대소문자 상관없이 input 값과 비교
                                    // (movieId, rating_sum, rating_num, rating_avg) 
                                    System.out.println("check_index: " + check_index);
                                    for (; check_index < count; check_index++) {
                                        if (movieGroup[check_index][0] == movieId) {
                                            movie_list[count_num][0] = Integer.toString(movieId);
                                            movie_list[count_num][1] = movieName;
                                            count_num++;
                                            System.out.println("count_num: " + count_num);
                                            flag = true;
                                            break;
                                        }   
                                    }
                                    if (flag) {
                                        flag2 = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (flag2)
                            break;
                    }
                    for (int i=0; i<10; i++) {
                        System.out.println("movie_list[][0]: " + movie_list[i][0]);
                        System.out.println("movie_list[][1]: " + movie_list[i][1]);
                    }

                    String linkline = "";
                    String [] link_list = new String[10];
                    while((linkline = linkbuf.readLine()) != null){
                        String[] linkwords = linkline.split("::");
                        Integer movieId = Integer.parseInt(linkwords[0]);
                        String imdbId = linkwords[1];
                        for (int i=0; i<10; i++) {
                            if (movieId == Integer.parseInt(movie_list[i][0])) {
                                link_list[i] = imdbId;
                            }
                        }
                    }

                    for (int i=0; i<10; i++)
                        System.out.println(movie_list[i][1] + " http://www.imdb.com/title/tt" + link_list[i]);                        
                }
            } else {
                System.out.println("A");
                String linkline = "";
                String [] link_list = new String[10];
                while((linkline = linkbuf.readLine()) != null){
                    String[] linkwords = linkline.split("::");
                    Integer movieId = Integer.parseInt(linkwords[0]);
                    String imdbId = linkwords[1];
                    for (int i=0; i<10; i++) {
                        if (movieId == movieGroup[i][0]) {
                            link_list[i] = imdbId;
                            System.out.println("link_list: " + link_list[i]);
                        }
                    }
                }
                while((movieline = moviebuf.readLine()) != null){
                    String[] moviewords = movieline.split("::");
                    Integer movieId = Integer.parseInt(moviewords[0]);
                    String movieName = moviewords[1];
                    // String moviegenre = moviewords[2];
                    // String[] genres = moviegenre.split("\\|"); // 특수문자 | 는 앞에 \\가 붙어야 구분가능
                    for (int i=0; i<10; i++) {
                        if (movieId == movieGroup[i][0]) {
                            System.out.println(movieName + " http://www.imdb.com/title/tt" + link_list[i]);                        
                        }
                    }
                }
            }
            
            System.out.println("count: "+count);
            System.out.println("ratecount: "+ratecount);
            System.out.println("UseridSize: "+userIdList.size());

            moviebuf.close();
            ratingbuf.close();
            userbuf.close();
            linkbuf.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error. The input must have two args like 'movie_genre' 'user_occupation' ");
        } catch(IOException e){
            System.out.println(e);
        }
    }
}
