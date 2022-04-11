import java.util.Random;

public class RandomSelect {
    public int randomSort(int[] array, int k){
           return rSort(array,0 ,array.length-1, k);
    }

    private int rSort(int[] array, int p, int r, int k) { //p는 배열의 마지막 시작지점, r은 배열의 마지막, k는 k번째 작은 수
        Random rand = new Random();
        int tmp;
        int q = p + rand.nextInt(r-p+1);
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
            return rSort(array, i + 1, r, k);
        } else {
            return rSort(array, p, i - 1, k);
        }

    }
}
