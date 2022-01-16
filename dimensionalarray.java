public class MyExample 
{
    public static void main(String[] args) 
    {
        // Declaration of Array
        int[] number;
        
        // Construction of Array with given size 
        // Here we are giving size 10 it mean it can hold 5 values of int type
        number =  new int[5];
        
        // Initialization of Array
        number[0] = 11;
        number[1] = 22;
        number[2] = 33;
        number[3] = 44;
        number[4] = 55;
        
        
        //Print the values from Array
        for(int i = 0; i < number.length; i++)
            System.out.println(number[i]);
        //multidimensional
        int arr[][]= {{2,3,4},{5,6,7},{8,9,0}};
        //printing 2D array
        for(int i=0;i<3;i++) {
        	for(int j=0;j<3;j++) {
        		System.out.println(arr[i][j]+" ");
        	}
        	System.out.println();
        	}
        }
        
     }
