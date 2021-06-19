 
![image](https://user-images.githubusercontent.com/38205047/122651200-3e803a80-d172-11eb-86d3-e5e57cc47f2f.png)

# User Guideline For The Application LINKER
This is an application that you can easily get movies recommended based on your personal information. We support 3 kinds of functions.    
- First, we basically recommend 10 best movies for anyone in our home page.     
- Also we show 10 recommended movies for popular genres such as “Action”, “Drama”, “Animation”.    
- Second, we recommend 10 best movies based on your user information and genre you prefer.    
- Third, we recommend number of movies that is related with the movie you like.    

# How LINKER Works    
Our program recommends movie based on following algorithm.

![image](https://user-images.githubusercontent.com/38205047/122651203-43dd8500-d172-11eb-99a1-812d896db24d.png)

- v: numbers of votes for the movie
- m: minimum votes required to be listed in the output (tota number of rate commits/ total number of movies)
- R: the average rating of the movie
- C: the mean vote across the whole report  
  
We recommend movies in order that has higher weighted ratings.    
### When Lack of Data Happens    
When we are lack of data which usually happens when we input detailed data, we subtract the conditions to increase the scope of data.     
- For first function which we input user information and genres, we subtract detailed conditions of user information first to earn data as much as possible. We subtract occupation first which is the most detailed class in user information. Then, we subtract age which is next detailed information. Since we always have more data for every input with gender and genre, we do not subtract more conditions after subtracting occupation and age.    
- For second function which we input movie that we like, we show data with proper genre, but when we lack of data, we would also show movies with different genre but high weighted ratings.

![image](https://user-images.githubusercontent.com/38205047/122651233-725b6000-d172-11eb-86ef-00e4f29eccf8.png)

# How To Use LINKER
For basic recommendation for popular genres, you don’t have to search for it. It appears automatically on the front page.    
#### 1. User Information Based Recommendation    
For other functions, you first select the type of searching to use our program.    
There are two searching types as follows.    

![image](https://user-images.githubusercontent.com/38205047/122651246-87d08a00-d172-11eb-8e3e-b27cf04aefa6.png)

For the first function, you can input 4types of information: Sex, Age, Occupation, Genre.    
All 4 input types are not mandatory. Therefore, you don’t have to select all the information for your input. You may not even input nothing and search the information for recommendation.    
We support inputs as follows:    
### - Sex (Single choice or no input):     
    Male, Female    
### - Age (Single choice or no input):     
    Under 17, 18~24, 25~34, 35~44, 45~49, 50~55, Over 56    
### - Occupation (Single choice or no input):     
    "other", "academic/educator", "artist", "clerical/admin", "college/gradstudent", "customerservice", "doctor/healthcare", "executive/managerial", "farmer", "homemaker", "K-12student", "lawyer", "programmer", "retired", "sales/marketing", "scientist", "self-employed", "technician/engineer", "tradesman/craftsman", "unemployed", "writer"
### - Genre (Multiple choice allowed or no input):     
    "Action", "Adventure", "Animation", "Children's", "Comedy", "Crime", "Documentary", "Drama", "Fantasy", "Film-Noir", "Horror", "Musical", "Mystery", "Romance", "Sci-Fi", "Thriller", "War", "Western"    

After selecting input, you should click search button. Then, the program would recommend you 10 movies based on your input and weighted rating.

#### 2. Movie Based Recommendation

![image](https://user-images.githubusercontent.com/38205047/122651251-93bc4c00-d172-11eb-9bef-835c795259f1.png)

For second function, you can input 2 types of information: Movie Title, Limit    
In this function, movie title is mandatory input. However, Limit is not a mandatory input and if you input nothing the default would be set to 10.    
We support inputs as follows:    
### - Movie Title (Mandatory input):    
    You should write full name of the movie including yea, for example, “Toy Story (1995)”     
### - Limit (Single input or no input):    
    You can write any number but if it is too large it takes a long time to output (small numbers under 1000 recommended), if you do not input anything, the default of this input is 10.    
    
After selecting input, you should click search button. Then, the program would recommend you the number of movies that you input which is related with the movie you input.    

After the search the output would be shown as follows.    


From the output, you can freely click the posters.    
Clicking the poster, it would lead you to the page to IMDB of the movie which has more detailed information about the movie.    

![image](https://user-images.githubusercontent.com/38205047/122651258-9ae35a00-d172-11eb-9c04-b99c46bfac28.png)


