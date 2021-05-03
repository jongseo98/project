package project2;

import org.junit.Test;
import static org.junit.Assert.*;

public class BranchCovTest {

    @Test
	public void getmainTest0() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="";
        arg[1]="";
        arg[2]="";
        arg[3]="";
		application.main(arg);
	}
    

	@Test
	public void getmainTest1() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="F";
        arg[1]="49";
        arg[2]="";
        arg[3]="";
		application.main(arg);
	}
    @Test
	public void getmainTest2() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="M";
        arg[1]="19";
        arg[2]="";
        arg[3]="";
		application.main(arg);
	}

    @Test
	public void getmainTest3() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="M";
        arg[1]="66";
        arg[2]="";
        arg[3]="";
		application.main(arg);
	}
    
    @Test
    public void getmainTest4() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="";
        arg[1]="37";
        arg[2]="gradstudent";
        arg[3]="";
		application.main(arg);
	}
    
    @Test
    public void getmainTest5() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="F";
        arg[1]="25";
        arg[2]="gradstudent";
        arg[3]="Horror";
		application.main(arg);
	}

    @Test
    public void getmainTest6() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="F";
        arg[1]="17";
        arg[2]="";
        arg[3]="Action|Adventure";
		application.main(arg);
	}
    
    @Test
    public void getmainTest7() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="F";
        arg[1]="55";
        arg[2]="";
        arg[3]="War|Crime|Drama";
		application.main(arg);
	}

    @Test
    public void getmainTest8() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="F";
        arg[1]="65";
        arg[2]="K-12student";
        arg[3]="War|Crime|Drama";
		application.main(arg);
	}

    @Test
    public void getmainTest9() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="F";
        arg[1]="65";
        arg[2]="academic";
        arg[3]="Adventure|Comedy";
		application.main(arg);
	}

    @Test
    public void getmainErr() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="i";
        arg[1]="";
        arg[2]="K-12student";
        arg[3]="War|Crime|Drama";
		application.main(arg);
	}

    @Test
    public void getmainErr2() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="F";
        arg[1]="-1";
        arg[2]="K-12student";
        arg[3]="War|Crime|Drama";
		application.main(arg);
	}

    @Test
    public void getmainErr3() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="F";
        arg[1]="24";
        arg[2]="woojin";
        arg[3]="";
		application.main(arg);
	}

    @Test
    public void getmainErr4() {
		Recommend application = new Recommend();
        String[] arg = new String[4];
        arg[0]="F";
        arg[1]="24";
        arg[2]="K-12student";
        arg[3]="woojin";
		application.main(arg);
	}

    @Test
    public void getmainErr5() {
		Recommend application = new Recommend();
        String[] arg = new String[2];
        arg[0]="F";
        arg[1]="24";
		application.main(arg);
	}

    
}