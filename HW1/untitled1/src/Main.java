import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        move(5);
        System.out.println(count);
    }
    public static long count = 0;
    public static int move(int k){
        count++;
       if (k==1 || k==2){
           return 1;
       } else{
           return move(k-1)+move(k-2);
       }
    }
}
