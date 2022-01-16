package accessModifiers;

public class methodExecution {

public int multipynumbers(int a,int b) {
	int z=a*b;
	return z;
}

public static void main(String[] args) {

	methodExecution b=new methodExecution();
	int ans= b.multipynumbers(11,5);
	System.out.println("Multipilcation is :"+ans);
	}


//call by value
public static class callMethod {

int val=156;

int operation(int val) {
	val =val*10/100;
	return(val);
}

public static void main(String args[]) {
	callMethod d = new callMethod();
	System.out.println("Before operation value of data is "+d.val);
	d.operation(100);
	System.out.println("After operation value of data is "+d.val);
	}
}
}