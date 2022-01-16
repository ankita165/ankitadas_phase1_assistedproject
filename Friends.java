package javaa;

import java.util.Scanner;

public class Friends {

public static void main(String[] args) {
Scanner input = new Scanner(System.in);
int no;
System.out.println("How many Friends you have");
no = input.nextInt(); 
String[] names = new String[no];

for(int counter = 0; counter <no; counter++) { System.out.println("Enter the name of the Friend "+(counter+1));
names [counter] = input.nextLine();

}
input.close();
System.out.println("Your Friends are");
for(int counter = 0; counter <no; counter++) { System.out.println(names [counter]);
System.out.println(names [counter]);

}
}
}