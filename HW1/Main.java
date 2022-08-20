import java.io.*;
import java.util.Random;

public class Main {
    public static void main(String[] args)  {
        try {
            String path = Main.class.getResource("").getPath();
            FileReader fr = new FileReader(path+"input.txt");
            BufferedReader br = new BufferedReader(fr);
            int num = Integer.parseInt(br.readLine()); //파일 첫번째 줄 읽어오기
            String[] nu = br.readLine().split(" "); //파일 두번째 줄 읽어오기
            int[] array = new int[num];
            int[] arrayRan = new int[num];
            int[] arrayDeter = new int[num];
            for (int i=0; i<num; i++){
                array[i] = Integer.parseInt(nu[i]); // int 형태로 num크기의 array에 저장
                arrayRan[i] = array[i];
                arrayDeter[i] = array[i];
            }
            int i = Integer.parseInt(br.readLine()); //파일 3번째 줄 읽어오기 (i번째로 작은 값)
            br.close();
            //1단계 종료

            long beforeTimeRan = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
            int ran = randomSort(arrayRan,i); //Run the randomized-select algorithm for the given input
            long afterTimeRan = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
            long secDiffTimeRan = afterTimeRan - beforeTimeRan; //시간 차이 계산.  measure the time

            File fileRandom = new File(path+"random.txt");
            BufferedWriter writerRandom = new BufferedWriter(new FileWriter(fileRandom));
            writerRandom.write(Integer.toString(ran)); //Print the i-th smallest number
            writerRandom.write("\r\n");
            writerRandom.write((secDiffTimeRan)+"ms"); //Print the time.
            writerRandom.close();
            //2단계 종료

            File fileCheck = new File(path+"result.txt");
            BufferedWriter writerCheck = new BufferedWriter(new FileWriter(fileCheck));
            writerCheck.write(checkerProgram(array, ran, i)); // Print the result of checking. 맞다면 true, 틀리다면 false (checks whether the output is correct or not)
            //checkerProgram은 array로 n elements를 가지고, ran으로 알고리즘 return 값, i를 가진다.
            writerCheck.close();
            //3단계 종료

            long beforeTimeDeter = System.currentTimeMillis(); // 코드 실행 전에 시간 받아오기
            int deter = deterministicSort(arrayDeter,i); //  Run the deterministic-select algorithm for the given input
            long afterTimeDeter = System.currentTimeMillis();// 코드 실행 후에 시간 받아오기
            long secDiffTimeDeter = afterTimeDeter - beforeTimeDeter; //시간 차이 계산. measure the time

            File fileDeter = new File(path+"deter.txt");
            BufferedWriter writerDeter = new BufferedWriter(new FileWriter(fileDeter));
            writerDeter.write(Integer.toString(deter)); //Print the i-th smallest number
            writerDeter.write("\r\n");
            writerDeter.write((secDiffTimeDeter)+"ms"); //Print the time.
            writerDeter.close();
            //4단계 종료

            BufferedWriter writerCheckDeter = new BufferedWriter(new FileWriter(fileCheck, true));
            writerCheckDeter.write("\r\n");
            writerCheckDeter.write(checkerProgram(array, deter, i)); // Check the correctness and Print the result
            writerCheckDeter.close();
            //5단계 종료
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int randomSort(int[] array, int k){
        return rSort(array,0 ,array.length-1, k);
    }

    private static int rSort(int[] array, int p, int r, int i) { //p는 배열의 마지막 시작지점, r은 배열의 마지막, i는 찾고자 하는 i번째 작은 수
        Random rand = new Random();
        int q = p + rand.nextInt(r-p+1); //java.util.Random의 nextInt를 활용해 0부터 (input의 크기-1)까지 랜덤하게 추출
        int tmp;
        int pivot = array[q]; //pivot
        tmp = array[q]; array[q] = array[r]; array[r] = tmp; //pivot을 배열의 가장 오른쪽으로 이동
        int h = p; //추후 피벗이 이동할 위치(기준점이라 하자)
        for (int j = p; j <= r - 1; j++) {
            if (array[j] <= pivot) {
                tmp = array[h]; array[h] = array[j]; array[j] = tmp; //pivot보다 작거나 같은 원소들은 좌측의 기준점에 있는 원소와 위치를 바꾼다
                h++; //기준점을 오른쪽으로 한칸 씩 이동
            }
        }
        tmp = array[h]; array[h] = array[r]; array[r] = tmp; //피벗을 기준점으로 이동. 이 때 pivot의 좌측에는 pivot보다 작거나 같은 원소만이 존재하며, 우측에는 큰 원소만이 존재한다.
        int count = h+1; //count는 현재의 배열에서 h+1번째로 작음을 의미한다.
        int[] array1 = new int[h-p]; //새로운 배열을 도입한 이유는 배열을 피벗을 기준으로 분리하고자 위함.
        int[] array2 = new int[r-h];
        for (int s=p; s<r+1; s++){
            if (s<h){
                array1[s] = array[s]; //피벗보다 작거나 같은 원소들은 array1에 저장
            } else if(s>h){
                array2[s-h-1] = array[s]; //피벗보다 크거나 같은 원소들은 array2에 저장
            }

        }
        if (i == count) {
            return pivot;
        } else if (i > count) {
            return rSort(array2, 0, r-h-1, i - count); //만약 (피벗의 배열 위치+1)보다 찾고자 하는 원소의 순서(i)가 크다면 오른쪽 배열(array2)에서 i-count 번째 원소를 찾도록 recusion
        } else {
            return rSort(array1, 0, h - p-1, i); //반대로 찾고자하는 원소의 순서(i)보다 (피벗의 배열 위치+1)가 크다면 왼쪽 배열(array1)에서 i번째 원소를 찾도록 recusion
        }

    }


    public static int deterministicSort(int[] array, int k){
        return dSort(array, 0,array.length-1, k);
    }

    private static int dSort(int[] array, int p, int r, int i){
        int o = partition(array, p,r); //random_select와의 차이점은 partiotion에서 존재
        int q = -1;
        for (int h=0; h<array.length; h++){
            if (array[h] == o){
                q = h; //partition함수에서는 recursion과정에서 나온 하나의 원소가 나오므로, 그 원소를 가지는 배열의 위치를 얻기 위해 수행한 for loop이다.
                break;
            }
        }
        int tmp; //이 아랫 부분은 random select와 동일하다
        int pivot = array[q]; //pivot
        tmp = array[q]; array[q] = array[r]; array[r] = tmp; //pivot을 배열의 가장 오른쪽으로 이동
        int h = p;
        for (int j = p; j <= r - 1; j++) {
            if (array[j] <= pivot) {
                tmp = array[h]; array[h] = array[j]; array[j] = tmp; //만약 pivot보다 작거나 같은 원소들은 좌측으로 이동시키며,
                h++; //추후 피벗이 이동할 위치를 오른쪽으로 한칸 씩 이동
            }
        }
        tmp = array[h]; array[h] = array[r]; array[r] = tmp; //피벗을 기준점으로 이동
        int count = h+1; //count는 현재의 배열에서 h+1번째로 작음을 의미한다.
        int[] array1 = new int[h-p]; //새로운 배열을 도입한 이유는 배열을 피벗을 기준으로 분리하고자 위함.
        int[] array2 = new int[r-h];
        for (int s=p; s<r+1; s++){
            if (s<h){
                array1[s] = array[s]; //피벗보다 작거나 같은 원소들은 array1에 저장
            } else if(s>h){
                array2[s-h-1] = array[s]; //피벗보다 크거나 같은 원소들은 array2에 저장
            }

        }
        if (i == count) {
            return pivot;
        } else if (i > count) {
            return dSort(array2, 0, r-h-1, i - count); //만약 (피벗의 배열 위치+1)보다 찾고자하는 원소의 순서(i)가 크다면 오른쪽 배열(array2)에서 i-count 번째 원소를 찾도록 recusion
        } else {
            return dSort(array1, 0, h - p-1, i); //반대로 찾고자하는 원소의 순서(i)보다 (피벗의 배열 위치+1)가 크다면 왼쪽 배열(array1)에서 i번째 원소를 찾도록 recusion
        }
    }

    private static int partition(int[] array, int p, int r){
        if ((r-p+1)<5){ //만약 input 배열의 크기가 5보다 작다면, 중앙값들의 배열을 따로 만들지 않고, 이 안에서 중앙값을 pivot으로 얻는다.
            int[] array00 = new int[r-p+1];
            for (int s=0; s<r-p+1; s++){
                array00[s] = array[p+s];
            }
            return check_median(array00);  //check_median함수 안에 insertion_sort 함수를 넣었다. 상수시간이 소요될 것이다.
        } else {
            int n =  (r - p + 1) / 5; //input 배열의 크기가 5보다 큰 경우
            int[] array0;
            if ((r - p + 1) % 5 ==0){
                array0 = new int[n]; //input 배열의 크기가 5의 배수라면(5*n) 중앙값들의 배열의 크기(array0)는 n.
            } else{
                array0 = new int[n +1]; //input 배열의 크기가 5의 배수가 아니라면(5*n+k) 중앙값들의 배열의 크기는 n+1, 마지막 배열의 크기는 k
            }
            for (int i = 0; i < n; i++) { //처음 n개 배열에서 중앙값
                int[] smallArray = new int[5];
                for (int j = 0; j < 5; j++) {
                    smallArray[j] = array[p+ 5 * i + j]; // 최초의 input 배열을 크기가 5가 되도록 분리한 뒤
                }
                array0[i] = check_median(smallArray); // insertion sort하고 중앙값을 얻어 array0에 저장.
            }
            if ((r - p + 1) %5 != 0){ //배열이 총 n+1개 존재하는 경우 마지막 배열을 다루는 if절
                int[] smallArray2 = new int[r-p+1 - 5*n]; //배열이 분리될 적 마지막 배열의 크기는 (입력되는 배열의 크기) - 5*n
                for (int j = 0; j < r-p+1 - 5*n; j++) {
                    smallArray2[j] = array[p+ 5 * n + j];
                }
                array0[n] = check_median(smallArray2);//마지막 배열에서도 마찬가지로 insertion sort하고 중앙값을 얻어 array0에 저장.
            }
            if (array0.length <= 5) { //중앙값들의 배열의 크기가 5보다 작거나 같으면 이 배열 안에서 중앙값을 구해 pivot을 얻는다.
                return check_median(array0);
            } else {
                if (array0.length  == n+1){
                    return partition(array0, 0, n); //중앙값들의 배열의 크기가 5보다 크다면, 중앙값 배열을 가지고 위 과정을 반복
                } else{
                    return partition(array0, 0, n-1);
                }
            }
        }
    }

    public static int check_median(int[] arr) {
        insertion_sort(arr, arr.length); //배열을 삽입정렬한다.
        int size=arr.length;
        int middle=size/2;
        if((size%2)==0) {
            return arr[middle-1]; //배열의 크기가 짝수면 find lower median
        }
        else{
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

    public static String checkerProgram(int[] array, int result, int check){
        int i = 0, j = 0;
        for (int k=0; k<array.length; k++){
            if (result>array[k]){
                i++;
            } else if (result==array[k]){
                j++;
            }
        }
        if ((check>i) && (check<=i+j) ){
            return "true";
        } else{
            return "false";
        }
    }
}
