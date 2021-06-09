// package project.project4;

// import org.junit.jupiter.api.Test;
// // import org.junit.Test;
// import org.junit.runner.RunWith;
// import static org.junit.Assert.*;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.boot.test.context.SpringBootTest;

// // @RunWith(SpringRunner.class)
// // @ContextConfiguration(classes = RecommendApplication.class)
// @SpringBootTest
// public class BranchCovTest {

//     @Test
// 	public void getmainTest0() {
//         Recommendation app = new Recommendation("F", "25", "gradstudent", "Action|War");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest1() {
//         Recommendation app = new Recommendation("M", "8", "", "");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest2() {
//         Recommendation app = new Recommendation("M", "22", "", "Comedy|Action|Crime|Horror|Thriller");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest3() {
//         Recommendation app = new Recommendation("M", "42", "", "Horror");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest4() {
//         Recommendation app = new Recommendation("M", "48", "farmer", "Film-Noir|Romance|Thriller");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest5() {
//         Recommendation app = new Recommendation("", "", "", "Adventure|Animation");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
//     public void getmainTest6() {
//         Recommendation app = new Recommendation("", "", "", "");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
//     public void getmainTest7() {
//         Recommendation app = new Recommendation("Toy Story (1995)", 1000);
//         app.getMovielist();
//         assertNotNull(app);
// 	}
    
//     @Test
//     public void getmainTest8() {
//         Recommendation app = new Recommendation("Toy Story (1995)", 10);
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
//     public void getmainTest9() {
//         Recommendation app = new Recommendation("Just Cause (1995)", 1000);
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
//     public void getmainTest10() {
//         Recommendation app = new Recommendation("Murder, My Sweet (1944)", 50);
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
//     public void getmainTest11() {
//         Recommendation app = new Recommendation("Force of Evil (1948)", 50);
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
//     public void getmainTest12() {
//         Recommendation app = new Recommendation("", "", "", "");
//         assertNotNull(app);
// 	}

//     @Test
//     public void getmainTest13() {
//         Recommendation app = new Recommendation("Force of Evil (1948)", 50);
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest14() {
//         Recommendation app = new Recommendation("Q", "42", "", "Horror");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest15() {
//         Recommendation app = new Recommendation("M", "52", "woojin", "Horror");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest16() {
//         Recommendation app = new Recommendation("M", "65", "gradstudent", "woojin");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest17() {
//         Recommendation app = new Recommendation("M", "-1", "gradstudent", "woojin|p");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest18() {
//         Recommendation app = new Recommendation("M", "-1", "gradstudent", "");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest20() {
//         RecommendController app = new RecommendController();
//         RecommendArgument2 recommend2 = new RecommendArgument2("Toy Story (1995)", 1);
//         app.recommendations2(recommend2);
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest21() {
//         Recommendation app = new Recommendation("Toy Story (1995)", 1);
//         app.getMovielist();
//         String s = app.toString();
//         assertNotNull(s);
// 	}

//     @Test
// 	public void getmainTest22() {
//         Recommendation app = new Recommendation("Usual Suspects, The (1995)", 15);
//         app.getMovielist();
//         String s = app.toString();
//         assertNotNull(s);
// 	}

//     @Test
// 	public void getmainTest23() {
//         Recommendation app = new Recommendation("F", "68", "gradstudent", "Action|War");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest24() {
//         RecommendController app = new RecommendController();
//         RecommendArgument2 recommend2 = new RecommendArgument2("Toy Story (1995)", 0);
//         app.recommendations2(recommend2);
//         assertNotNull(app);
// 	}

//     @Test
// 	public void getmainTest25() {
//         Recommendation app = new Recommendation("M", "52", "farmer", "Film-Noir|Romance|Thriller");
//         app.getMovielist();
//         assertNotNull(app);
// 	}

// }