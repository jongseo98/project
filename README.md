### 1. Milestone3 Algorithm (Part2) :: implemented in Recommendation.java in project3

In milestone3(part2), we get title of movie and limit as input. When we input the title of movie, the movie has its own genre or genres (one or more). Our algorithm recommends movie that has at least one same genre with the input and recommend them in order that has higher ratings.     
For example, when we input “Toy Story (1995)”, our algorithm first finds “Toy Story” genres which are Animation, Children's, Comedy. After that, it is pretty much similar with milestone2.

It will find same movies as milestone2 with input “”, “”, “”. “Animation, Children's, Comedy”. Same as milstone2, we only deal with data that has enough rating commitments. We consider data that has less rating commitments than average as invalid data.     
For example, we do not recommend movies that average rating is 5 but only has 2 rating commitments. We only recommend movies that has more commitments than average (when 10 movies and 70 rating commitments, only movies with more than 7 rating commitments are valid).
Within valid movies we recommend movies in order that has higher average rating.

As explained in milestone, default of limit is 10.    
For particular case with high limits, we have one more algorithm, for example, “Force of Evil (1948)” “limit:70”. “Force of Evil (1948)” ‘s genre is “Film-Noir” but only has 44 movies in genre data base which is lower than limit 70. In this case, we first recommend film that has genre of “Film-Noir” first and then recommend any other films with any other genres in order that has higher average rating.  In this case, we print out all “Film-Noir” movies even the movies do not have more commitments than average. However, we only recommend movies that has more commitments than average for the movies that are not “Film-Noir”.

#### In detail    
###### A. Input: “Toy Story (1995)” "limit:20"    
     1. (Has at least one of Animation, Children's, Comedy),(Has more commitments than average),(Highest average rating among valid movies)    
     ...    
     19.(Has at least one of Animation, Children's, Comedy),(Has more commitments than average),(19th Highest average rating among valid movies)    
     20.(Has at least one of Animation, Children's, Comedy),(Has more commitments than average),(20th Highest average rating among valid movies)    
     
###### B. Input: “Force of Evil (1948)” “limit:70”    
     1. (Has genre Film-Noir),(Highest average rating)
     ...
     44.(Has genre Film-Noir),(44th Highest average rating)    
     45.(Has more commitments than average)(1st Highest average rating among valid movies)    
     46.(Has more commitments than average)(2nd Highest average rating among valid movies)    
     ...    
     70.(Has more commitments than average)(36th Highest average rating among valid movies)    
