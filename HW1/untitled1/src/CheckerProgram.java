public class CheckerProgram {
    public void checkerProgram(int[] array, int k, int n){
        int s = 0;
        for (int i=0; i<array.length; i++){
            if (array[i]<k){
                s++;
            }
        }
        if (s+1 ==n){
            System.out.println("선형시간 완료");
        }
    }
}
