package com.Project;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EmailValidation {
	
    public static boolean isValidEmail(String email) {
    	
		String regex = "^(.+)@(.+)$";
		//initialize the Pattern object

		return  Pattern.matches(regex, email);
    }
     
     public static void main(String[] args) {
    	 Scanner sc = new Scanner(System.in);
		 ArrayList<String> emails = new ArrayList<String>();
		 String input;
		 Boolean emailFoundFlag = false;
		 // valid email addresses
		 emails.add("ankita@example.com");
		 emails.add("mama@example.com");
		 emails.add("debadarsani@example.me.org");
		 //invalid email 
		 emails.add("ashok.example.com");
		 emails.add("@example.com");
		 emails.add("example.com@");
		 
		do {
			 System.out.println("Enter the valid email address to search");
		     input = sc.nextLine();
		 }while(!isValidEmail(input));
		
	     
         for (String value : emails) {
        	 if(value.equalsIgnoreCase(input)) {
        		 emailFoundFlag= true;
        		 break;
        	 }
         }
         if(emailFoundFlag == true) {
        	 System.out.println("Ëmail Found");
         }
         else {
        	 System.out.println("Email Not Found");
         }
     }
}