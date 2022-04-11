import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DeterministicSelect {
    public int deterministicSort(int[] array, int k){
        return qSort(array, 0,array.length-1, k);
    }

    private int qSort(int[] array, int p, int r, int k){
        int q = partition(array, p,r);
        int tmp;
        int pivot = array[q];
        tmp = array[q]; array[q] = array[r]; array[r] = tmp;
        int i = p;
        for (int j = p; j <= r - 1; j++) {
            if (array[j] <= pivot) {
                tmp = array[i]; array[i] = array[j]; array[j] = tmp;
                i++;
            }
        }
        tmp = array[i]; array[i] = array[r]; array[r] = tmp;
        int count = i+1;
        if (k == count) {
            return pivot;
        } else if (k > count) {
            return qSort(array, i + 1, r, k);
        } else {
            return qSort(array,p,i-1, k);
        }
    }

    private int partition(int[] array, int p, int r){
        if ((r-p+1)<5){
            int[] array00 = new int[r-p+1];
            for (int s=0; s<r-p+1; s++){
                array00[s] = array[p+s];
            }
            return check_median(array00);
        } else {
            int n = (int) ((r - p + 1) / 5);
            int[] array0 = new int[n + 1];
            for (int i = 0; i < n; i++) {
                int[] smallArray = new int[5];
                for (int j = 0; j < 5; j++) {
                    smallArray[j] = array[5 * i + j];
                }
                array0[i] = check_median(smallArray);
            } //처음 n개 배열에서 중앙값
            int[] smallArray = new int[array.length - 5*n];
            for (int j = 0; j < array.length - 5*n; j++) {
                smallArray[j] = array[5 * n + j];;
            }
            array0[n] = check_median(smallArray); //마지막 배열에서 중앙값

            if (array0.length <= 5) {
                return check_median(array0);
            } else {
                return partition(array0, 0, n);
            }
        }
    }

    public static int check_median(int[] arr)        //calculation of median
    {
        insertion_sort(arr, arr.length);
        int size=arr.length;
        int middle=size/2;
        if((size%2)==0)                                       //Length of list is even
        {
            return arr[middle-1];
        }
        else                                                 //Length of list is odd
        {
            return arr[middle];
        }
    }

    private static void insertion_sort(int[] a, int size) {
        for(int i = 1; i < size; i++) {
            int target = a[i];
            int j = i - 1;
            while(j >= 0 && target < a[j]) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = target;
        }

    }
}
