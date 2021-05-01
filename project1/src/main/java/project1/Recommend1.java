package project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
            int age_input = Integer.parseInt(args[1]);
            String occup_input = args[2];

            System.out.println("gender_input: " + gender_input);
            System.out.println("age_input: " + age_input);
            System.out.println("occup_input: " + occup_input);

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
            String age_index;
            if (age_input < 18) {
                age_index = "1";
            } else if (age_input < 25) {
                age_index = "18";
            } else if (age_input < 35) {
                age_index = "25";
            } else if (age_input < 45) {
                age_index = "35";
            } else if (age_input < 50) {
                age_index = "45";
            } else if (age_input < 56) {
                age_index = "50";
            } else {
                age_index = "56";
            }

            System.out.println("age_index: " + age_index);

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
                        if (s.equalsIgnoreCase(occup_input) && userGender.equalsIgnoreCase(gender_input) && userAge.equals(age_index))
                            userIdList.add(userId);
                } else {
                    if (occup_list[userOccup].equalsIgnoreCase(occup_input) && userGender.equalsIgnoreCase(gender_input) && userAge.equals(age_index))
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

            


            // Arrays.sort(movieGroup, new Comparator<double[]>(){
            //     public int compare(double[] o1, double[] o2) {
            //         return Double.compare(o2[2], o1[2]);
            //     }
            // });
            
            int num = 0;
    

            for(int i=0 ; i<count; i++) { 
                for(int j=0 ; j<4; j++){ 
                    if (movieGroup[count-i-1][2] >= (ratecount / count)) {
                        num++;
                        System.out.println("movieGroup["+(count-i-1)+"]["+j+"] = " + movieGroup[count-i-1][j]);
                    }
                } 
            }
            System.out.println(num/4);
            System.out.println("count: "+count);
            System.out.println("ratecount: "+ratecount);
            

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
