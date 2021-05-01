package project1;

import java.util.ArrayList;

public class Test2 {
    public static void main( String[] args ) {
        ArrayList<ArrayList<String>> mGroupList = new ArrayList<ArrayList<String>>(); 
        ArrayList<String> mChildList = new ArrayList<String>();

        mChildList.add("child1"); 
        mChildList.add("child2"); 
        mChildList.add("child3"); 
        mGroupList.add(mChildList);

        mChildList = new ArrayList<String>(); 
        mChildList.add("new child4"); 
        mChildList.add("new child5"); 
        mChildList.add("new child6"); 
        mGroupList.add(mChildList); 

        for(int i=0 ; i<mGroupList.size() ; i++) { 
            System.out.println("array: " + i); 
            for(int j=0 ; j<mChildList.size() ; j++){ 
                System.out.println("array: " + mGroupList.get(i).get(j)); 
            } 
        }
    }
}
