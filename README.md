# 1.Project2 Algorithm

In project2, we get 3 to 4 arguments as an input.       
First argument is gender, second is age, third is occupation, fourth is genre.           
Our algorithm gets the argument and makes the userIdList that equals the input (gender, age, occupation) in users.dat file. 
userIdList contains (userId).
By using userIdList, we makes movieGroup that equals userId in userIdList for rating.dat file.
movieGroup contains (movieId, rating_sum, rating_num, rating_avg).
Sort movieGroup in order of rating_avg, if same, sort order of rating_num. (descending order)
if genre input exists, among the movies in movieGroup, 10 movies that equals the genre input in order of index, extract and add to the movielist for movies.dat file.
else if genre input doesn't exists, extract 10 movies in the movieGroup in order of index, add to the movielist for movies.dat file.
movie_list contains (movieId, movieName).
In links.dat, make link_list that equals movieId in movie_list.
link_list contains (imdbId).
Print movie_list and link_list.          

### Criteria of data:
When userIdList is given, there will be the number of ratings committed by user ID and the number of movies that are rated by userIdList. We divide number of ratings committed(ratecount) by number of movies rated by movieGroup(count), so that we can achieve average number of ratings committed to each movie by user groups. We define data as valid only if the movie has more ratings committed than the number of ratings committed to each movie by user groups.          
         
Example) ratecount=1000, count=100 -> ratecount/count=10          
	Movies with at least 10 rating commits by userIdLists are valid data.            
	5star average rating with 9 rating commits by userIdList are not recommended.           
After validation of data, we recommend movies with Top 10 average ratings in valid data.            
If the average ratings are same, movie with more rating commits takes the priority.            
If there are less than 10 movies that are valid, we disregard occupation as an input so that we can have more data that are valid.            

Our interpretation:            
We interpreted “relevance” as the validity of data which is meaningful. So, we defined data as valid only if the movie has more ratings committed than the number of ratings committed to each movie by user groups.            
Also, for ‘similarity’, we thought that Gender(>age>occupation) has more priority in similarity. Therefore, we deleted occupation first when we had less than 10 movies to recommend.               

# 2. How to run a program
Before you begin, java11 and maven are must installed in your system.             
You can run with the following commands.             

```
#!/bin/bash
git clone https://github.com/20171119/project.git
cd project/project2
mvn install
mvn assembly:assembly
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar project2.Recommend "" "" ""
java -cp target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar project2.Recommend "" "" "" "Adventure"

```


# 3. Input and Output Handling
Our program supports such inputs and outputs:          
1) If you input less than 3 arguments, ex) “F” “ ”            
Prints out-> "Error. The number of input should be at least 3."              
2.) If you put wrong Gender, ex) “L” “25” “farmer”          
Prints out-> "Error. User gender is F or M"          
3) If you input wrong age, ex) “M” “-30” “homemaker”          
Prints out-> "Error. User age cannot be under 0"            
4) If you input the occupation not in the occupation list, ex) “M” “25” “Wizard” or “M” “25” “Grad student”(can't recognize space)
Prints out-> "Error. The occupation does not exist"           
5) If you input genre not in the genre list, ex) “F” 65” “artist” “American”          
Prints out-> "Error. The movie genre does not exist"          
6)If you put all inputs are right in form it prints out output as:   
               
Input: "" "" "" "Adventure"           
         
Output:           
Raiders of the Lost Ark (1981) http://www.imdb.com/title/tt0082971           
Star Wars: Episode IV - A New Hope (1977) http://www.imdb.com/title/tt0076759            
Lawrence of Arabia (1962) http://www.imdb.com/title/tt0056172            
Great Escape, The (1963) http://www.imdb.com/title/tt0057115            
Princess Bride, The (1987) http://www.imdb.com/title/tt0093779          
Star Wars: Episode V - The Empire Strikes Back (1980) http://www.imdb.com/title/tt0080684             
Treasure of the Sierra Madre, The (1948) http://www.imdb.com/title/tt0040897           
African Queen, The (1951) http://www.imdb.com/title/tt0043265           
Wizard of Oz, The (1939) http://www.imdb.com/title/tt0032138          
Princess Mononoke, The (Mononoke Hime) (1997) http://www.imdb.com/title/tt0119698           

# 4. Role of each member
20171119 박종서: Overall management handling Github and java codes.            
20171229 정우진: Managing algorithms and error handling of program.            
20171273 황태영: Managing with Branch coverage and Docker.          
20171084 김태훈: Managing with Branch coverage and maven.               
***
These were main parts that we managed and it doesn’t mean that we worked only for each part.            
Every participated actively during the whole milestone.            
In this milestone, we used Live Share in Visual Studio to code java code concurrently between teammates since we all participated on making codes.            
