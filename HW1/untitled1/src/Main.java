import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        int[] array = {1,4, 300, 10, 7, 9,3,5}; //array 받아들이기, 시간 측정, test maker
        RandomSelect randomSelect = new RandomSelect();
        DeterministicSelect deterministicSelect = new DeterministicSelect();
        System.out.println(deterministicSelect.deterministicSort(array, 3));
        CheckerProgram checker = new CheckerProgram();
    }

}
