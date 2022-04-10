public class CheckerProgram {
    public void checkerProgram(int[] array){
        int n=0;
        for (int i=0; i<array.length-1; i++){
            if (array[i]<array[i+1]){
                n++;
            }
        }
        if (n == array.length-1){
            System.out.println("선형시간 완료");
        }
    }
}
