package com.sl.Assignments.PracticeProject;
import java.io.File;
	import java.io.IOException;
	import java.util.Arrays;
	import java.util.Scanner;

public class P1_FileManipulation {
	 String userDir = System.getProperty("user.dir");
	 File userFolder;

	 public P1_FileManipulation() {
	    		
		 userFolder = new File(userDir+"/files");
	     if (!userFolder.exists()) {
	    	 userFolder.mkdirs(); 
	     }	
	 	 System.out.println("User Directory : "+ userFolder.getPath());
	    }    

	    void showPrimaryMenu() {
	    	System.out.println("\nMAIN MENU - Select any of the following: \n"+
                    "Enter \"1\" to List files in the directory\n"+
                    "Enter \"2\" Add, Delete or Search files\n"+
                    "Enter \"3\" to Exit the Program");
	        try(Scanner scanner = new Scanner(System.in)){
	            
	        	int option = scanner.nextInt();
	            switch (option){
	                case 1 : {
	                    showFiles();
	                    showPrimaryMenu();
	                }
	                case 2 : {
	                    showSecondaryMenu();
	                }
	                case 3 : {
	                    System.out.println("Thank You Visit Again");
	                    System.exit(0);
	                }
	                default: showPrimaryMenu();
	            }
	        }
	        catch (Exception e){
	            System.out.println("Invalid Input \n "
	            		+ "Please enter \"1\", \"2\" or \"3\" to proceed");
	            showPrimaryMenu();
	        }
	    }

	    void showSecondaryMenu() {
	    	System.out.println("\nSelect any of the following: \n"+
                    "Enter \"a\" to Add a new record\n"+
                    "Enter \"b\" to Delete the existing record\n"+
                    "Enter \"c\" to Search for the record\n"+
                    "Enter \"d\" to GoBack to Previous Menu");
	        try(Scanner scanner = new Scanner(System.in))
	        {
	            char[] input = scanner.nextLine().toLowerCase().trim().toCharArray();
	            char option = input[0];
	            String fileName;

	            switch (option){
	                case 'a' : {
	                    System.out.print("Adding a data...Please Enter  data Name : ");
	                    fileName = scanner.next().trim().toLowerCase();
	                    addFile(fileName);
	                    break;
	                }
	                case 'b' : {
	                    System.out.print("Deleting a data...Please Enter  data Name : ");
	                    fileName = scanner.next().trim();
	                    deleteFile(fileName);
	                    break;
	                }
	                case 'c' : {
	                    System.out.print("Searching a data...Please Enter  data Name : ");
	                    fileName = scanner.next().trim();
	                    searchFile(fileName);
	                    break;
	                }
	                case 'd' : {
	                    System.out.println("Going Back to MAIN menu");
	                    showPrimaryMenu();
	                    break;
	                }
	                default : System.out.println("Please enter \n"
	                		+ "\"a\", \"b\", \"c\" or \"d\"");
	            }
	            showSecondaryMenu();
	        }
	        catch (Exception e){
	            System.out.println("Please enter \n"
	            		+ "\"a\", \"b\", \"c\" or \"d\"");
	            showSecondaryMenu();
	        }
	    }

	    void showFiles() {
	        if (userFolder.list().length==0)
	            System.out.println("The folder is empty");
	        else {
	            String[] list = userFolder.list();
	            System.out.println("The data in "+ userFolder +" are :");
	            Arrays.sort(list);
	            for (String str:list) {
	                System.out.println(str);
	            }
	        }
	    }

	    void addFile(String filename) throws IOException {
	        File filepath = new File(userFolder +"/"+filename);
	        String[] list = userFolder.list();
	        for (String file: list) {
	            if (filename.equalsIgnoreCase(file)) {
	                System.out.println("data " + filename + " already exists at " + userFolder);
	                return;
	            }
	        }
	        filepath.createNewFile();
	        System.out.println("data "+filename+" added to "+ userFolder);
	    }

	    void deleteFile(String filename) {
	        File filepath = new File(userFolder +"/"+filename);
	        String[] list = userFolder.list();
	        for (String file: list) {
	            if (filename.equals(file) && filepath.delete()) {
	                System.out.println("data " + filename + " deleted from " + userFolder);
	                return;
	            }
	        }
	        System.out.println("Delete Operation failed. FILE NOT FOUND");
	    }

	    void searchFile(String filename) {
	        String[] list = userFolder.list();
	        for (String file: list) {
	            if (filename.equals(file)) {
	                System.out.println("FOUND : data " + filename + " exists at " + userFolder);
	                return;
	            }
	        }
	        System.out.println("No Data Found (NDF)");
	    }

	    public static void main(String[] args) {
	        System.out.println("*   Project_1	*"); 
	        P1_FileManipulation menu = new  P1_FileManipulation();
	        menu.showPrimaryMenu();
	    }
	}
