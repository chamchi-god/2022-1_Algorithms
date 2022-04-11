import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        int[] array = {1,4, 300, 10, 7, 9,3,5}; //array 받아들이기, 시간 측정
        RandomSelect randomSelect = new RandomSelect();
        DeterministicSelect andomSelect = new DeterministicSelect();
        System.out.println(randomSelect.randomSort(array, 3));
        CheckerProgram checker = new CheckerProgram();
        checker.checkerProgram(array, randomSelect.randomSort(array, 3), 3);

    }

}
