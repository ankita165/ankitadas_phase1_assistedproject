package accessModifiers;

public class constructor {
	
	    int id;  
	    String name;  
	    // creating default constructor
	     constructor()
	   {
	   }


	    //creating a parameterized constructor  
	    constructor(int i,String n){  
	    id = i;  
	    name = n;  
	  }  
	    
	    public static void main(String args[]){  
	    constructor e2 = new constructor(1,"ankita"); 
	    System.out.println(e2.id);//prints 1
	    System.out.println(e2.name);//prints ankita
	    
	   
	   }  
	}

