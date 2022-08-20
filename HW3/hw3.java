import java.io.*;
import java.util.*;

public class hw3 {
    static int time = 0;
    static int[] times;
    public static void main(String[] args) {
        String path = hw3.class.getResource("").getPath();
        try {
            //program 1: Read the input graph from an input file
            FileReader fr = new FileReader(path + args[0]);
            BufferedReader br = new BufferedReader(fr);

            ArrayList listInput = new ArrayList();
            listInput.add(0);

            int vertexNum = Integer.parseInt(br.readLine()); //The input file contains vertex num in the first line
            for (int i = 0; i < vertexNum; i++) {
                listInput.add(br.readLine());     //i번째 vertex의 edge에 대해 i번째 listInput에 저장
            }

            if (args[2].equals("adj_mat")){
            int[][] ad_max = graph_mat(vertexNum, listInput, "mat"); //store the graph into adjacency matrix
            int[][] ad_max_rev = graph_mat(vertexNum, listInput, "matrev"); //2. Compute GR (transpose of G) where direction of each edge in G is reversed.

            //program 2-1:Run your program when the graph is given as an adjacency matrix, and measure the time.
            long bTMat = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
            int[] timesMat = dfs_mat(vertexNum, ad_max); //1. Run DFS on G to compute finish time f[v] for each vertex v.
            //2.Compute GR (transpose of G)은 위에서 미리 구했다.
            ArrayList<ArrayList> resultMat = dfs_mat_rev(vertexNum, ad_max_rev, min(timesMat)); //3. Run DFS on GR (in the main loop of DFS, consider vertices in decreasing order of f [v])
            //4. "resultMat" stores Output each tree made in 3.
            long aTMat = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
            long secDiffMat = aTMat - bTMat; //시간 차이 계산.  measure the time
            //program 2-2: Print the output and the time
            File fileOutputMat = new File(path + args[1]);
            BufferedWriter writerOutputMat = new BufferedWriter(new FileWriter(fileOutputMat));
            resultMat = lexicographic_order(resultMat);

            for (int i = 0; i < resultMat.size(); i++){
                if (resultMat.get(i).size() != 0) {
                    writerOutputMat.write(resultMat.get(i).toString().substring(1, resultMat.get(i).toString().length() - 1).replaceAll(",", ""));  //Print the output
                    writerOutputMat.newLine();
                }
            }
            writerOutputMat.write(secDiffMat+"ms"); //Print the time
            writerOutputMat.close();
            } else if(args[2].equals("adj_list")){
            ArrayList<LinkedList> ad_list = graph_list(vertexNum, listInput, "list");//store the graph into adjacency  List
            ArrayList<LinkedList> ad_list_rev = graph_list(vertexNum, listInput, "listrev"); //2. Compute GR (transpose of G) where direction of each edge in G is reversed.

            //program 3-1:Run your program when the graph is given as an adjacency list, and measure the time.
            long bTList = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
            int[] timesList = dfs_list(vertexNum, ad_list); //1. Run DFS on G to compute finish time f[v] for each vertex v.
            //2.Compute GR (transpose of G)은 위에서 미리 구했다.
            ArrayList<ArrayList> resultList = dfs_list_rev(vertexNum, ad_list_rev, min(timesList)); //3. Run DFS on GR (in the main loop of DFS, consider vertices in decreasing order of f [v]).
            //4."resultList" stores Output each tree made in 3.
            long aTList = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
            long secDiffList = aTList - bTList; //시간 차이 계산.  measure the time

            //program 3-2: Print the output and the time
            File fileOutputList = new File(path + args[1]);
            BufferedWriter writerOutputList = new BufferedWriter(new FileWriter(fileOutputList));
            resultList = lexicographic_order(resultList);
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).size() != 0) {
                    writerOutputList.write(resultList.get(i).toString().substring(1, resultList.get(i).toString().length() - 1).replaceAll(",", ""));  //Print the output.
                    writerOutputList.newLine();
                }
            }

            writerOutputList.write(secDiffList+"ms"); //Print the time
            writerOutputList.close();
            } else {
            int[] ad_array0 = graph_array_list0(vertexNum, listInput, "array"); //store the graph into adjacency array
            ArrayList ad_array1 = graph_array_list1(vertexNum, listInput, "array"); //store the graph into adjacency array
            int[] ad_array0_rev = graph_array_list0(vertexNum, listInput, "arrayrev"); //2. Compute GR (transpose of G) where direction of each edge in G is reversed.
            ArrayList ad_array1_rev = graph_array_list1(vertexNum, listInput, "arrayrev");

            //program 4-1:Run your program when the graph is given as an adjacency array, and measure the time.
            long bTArray = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
            int[] timesArray = dfs_array(vertexNum, ad_array0, ad_array1); //1. Run DFS on G to compute finish time f[v] for each vertex v.
            //2.Compute GR (transpose of G)은 위에서 미리 구했다.
            ArrayList<ArrayList> resultArray = dfs_array_rev(vertexNum, ad_array0_rev, ad_array1_rev, min(timesArray)); //3. Run DFS on GR (in the main loop of DFS, consider vertices in decreasing order of f [v]).
            //4. "resultArray" stores Output each tree made in 3.
            long aTArray = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
            long secDiffArray = aTArray- bTArray; //시간 차이 계산.  measure the time

            //program 4-2: Print the output and the time
            File fileOutputArray = new File(path + args[1]);
            BufferedWriter writerOutputArray = new BufferedWriter(new FileWriter(fileOutputArray));
            resultArray = lexicographic_order(resultArray);

            for (int i = 0; i < resultArray.size(); i++) {
                if (resultArray.get(i).size() != 0) {
                    writerOutputArray.write(resultArray.get(i).toString().substring(1, resultArray.get(i).toString().length() - 1).replaceAll(",", ""));  // Print the output.
                    writerOutputArray.newLine();
                }
            }

            writerOutputArray.write(secDiffArray+"ms"); // Print the time.
            writerOutputArray.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<ArrayList> lexicographic_order(ArrayList<ArrayList> result){
        ArrayList<ArrayList> orderResult = new ArrayList();
        for (int i=0; i<result.size(); i++){
            if (result.get(i).size()!=0){ //result.get(i)가 빈 ArrayList가 아닌 경우
                if (orderResult.size() == 0){ //orderResult에 아직 아무 ArrayList가 존재하지 않는 경우
                    orderResult.add(result.get(i));
                } else{
                    for (int j=0; j<orderResult.size(); j++){
                        if (compare(i, orderResult.get(j).get(0).toString())[0].equals(String.valueOf(i))){ //Arraylist result에서 각 원소에 저장된 Arraylist의 가장 앞에 있는 수와 result에서의 위치가 동일하다는 사실을 이용한 것이다.
                            orderResult.add(j, result.get(i));
                            break;
                        } else if(compare(i, orderResult.get(orderResult.size()-1).get(0).toString())[1].equals(String.valueOf(i))){
                            orderResult.add(result.get(i)); //가장 뒤에 삽입
                            break;
                        }

                    }
                }
            }
        }
        return orderResult;
    }

    static String[] compare(int string1, String string2){
        int n=2;
        String[] words = new String[2];
        words[0] = String.valueOf(string1);
        words[1] = string2;
        for(int i = 0; i < n-1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (words[i].compareTo(words[j]) > 0) { //string간 compareTo를 활용해 비교.
                    String temp = words[i];
                    words[i] = words[j];
                    words[j] = temp;
                }
            }
        }
        return words;
    }

    static ArrayList min(int[] times){ //consider vertices in decreasing order of  finish time f [v].
        ArrayList times0 = new ArrayList();
        times0.add(0);
        for (int i=1; i<times.length; i++){
            int min = 10000000;
            for (int j=1; j<times.length; j++){
                if(min>times[j]) { //min의 값보다 times[j]가 작으면 min = times[j]
                    min = times[j];
                }
            }

            for (int j=1; j<times.length; j++){
                if(min==times[j]) {
                    times[j]=10000000; //min의 값을 가지는 j번째 원소에 대해 10000000으로 값을 바꿔 j번째 원소는 더이상 최소값이 되지 못하도록함.
                    times0.add(j); //times0에 j 삽입
                }
            }
        }
        return times0; //위 과정을 반복하면 times0는 vertices in increasing order of  finish time f [v]가 됨. dfs에서 이 부분을 고려할 예정.
    }

    static int[] dfs_mat(int vertexNum, int[][] ad_max) {
        boolean[] visited = new boolean[vertexNum + 1]; //각 노드의 방문여부 확인을 위한 배열
        times = new int[vertexNum + 1]; // finish time f [v]
        time = 0; //f [v]에 포함될 각 원소

        for (int j = 1; j < visited.length; j++) {
            if (!visited[j]) { //노드를 방문하지 않았다면,
                aDFS_mat(j, visited, ad_max); //aDFS 실행
            }
        }
        return times;
    }

    static void aDFS_mat(int v, boolean visited[], int[][] ad_max) {
        visited[v] = true; // 현재 노드를 방문한 것으로 체크

        for (int i = 1; i < visited.length; i++) {
            if (ad_max[v][i] == 1 && !visited[i]) {  // 방문한 노드와 인접한 모든 노드를 가지고 온다
                aDFS_mat(i, visited, ad_max); //방문한 노드의 인접한 노드 중 인접하지 않은 노드에 aDFS 수행
            }
        }
        ++time; //어떤 노드에 대해 더이상 aDFS를 진행할 수 없다면, ++time후 times[v]에 삽입.
        times[v] = time;
    }

    static ArrayList<ArrayList> dfs_mat_rev(int vertexNum, int[][] ad_max, ArrayList min) {
        boolean[] visited = new boolean[vertexNum + 1]; //각 노드의 방문여부 확인을 위한 배열
        ArrayList<ArrayList> arrayList = new ArrayList();; //Run DFS on GR 과정에서 얻은 Output을 저장하기 위한 arraylist<arraylist>.

        for (int i = 0; i < visited.length; i++) {
            arrayList.add(new ArrayList());
        }

        for (int j = vertexNum; j >= 1; j--) { //이전의 min함수를 통해 만들어진 arraylist가 increasing order of finish time f[v]이므로 큰 수부터 for문 진행.
            if (!visited[(int) min.get(j)]) {
                ArrayList arrayList0 = new ArrayList(); //방문한 노드들을 저장할 ArrayList arrayList0.
                Collections.sort(aDFS_mat_rev((int) min.get(j), visited, ad_max, arrayList0)); //vertices in a line are sorted
                arrayList.remove((int) arrayList0.get(0));
                arrayList.add((Integer) arrayList0.get(0), arrayList0); //arrayList0.get(0)를 통해 arrayList0에서 가장 작은 수에 해당하는 위치에 arraylist를 저장.

            }
        }
        return arrayList;

    }

    static ArrayList aDFS_mat_rev(int v, boolean visited[], int[][] ad_max, ArrayList arrayList0) {
        visited[v] = true; // 현재 노드를 방문한 것으로 체크
        arrayList0.add(v); //방문한 노드를 arrayList0에 저장
        for (int i = 1; i < visited.length; i++) {
            if (ad_max[v][i] == 1 && !visited[i]) { // 방문한 노드와 인접한 모든 노드를 가지고 온다
                aDFS_mat_rev(i, visited, ad_max, arrayList0); //방문한 노드의 인접한 노드 중 인접하지 않은 노드에 aDFS 수행
            }
        }
        return arrayList0;
    }

    static int[] dfs_list(int vertexNum, ArrayList ad_list) {
        boolean[] visited = new boolean[vertexNum + 1]; //각 노드의 방문여부 확인을 위한 배열
        times = new int[vertexNum + 1]; // finish time f [v]
        time = 0; //f [v]에 포함될 각 원소

        for (int j = 1; j < visited.length; j++) {
            if (!visited[j]) { //노드를 방문하지 않았다면,
                aDFS_list(j, visited, ad_list); //aDFS 실행.
            }
        }

        return times;
    }

    static void aDFS_list(int v, boolean visited[], ArrayList<LinkedList> ad_list) {
        visited[v] = true; // 현재 노드를 방문한 것으로 체크

        Iterator<Integer> it = ad_list.get(v).listIterator();
        while (it.hasNext()) {
            int n = it.next();
            if (!visited[n]) {
                aDFS_list(n, visited, ad_list);
            }
        }

        ++time; //어떤 노드에 대해 더이상 aDFS를 진행할 수 없다면, ++time후 times[v]에 삽입.
        times[v] = time;
    }

    static ArrayList<ArrayList> dfs_list_rev(int vertexNum, ArrayList ad_list, ArrayList min) {
        boolean[] visited = new boolean[vertexNum + 1]; //각 노드의 방문여부 확인을 위한 배열
        ArrayList<ArrayList> arrayList = new ArrayList(); //Run DFS on GR 과정에서 얻은 Output을 저장하기 위한 arraylist<arraylist>.

        for (int i = 0; i < visited.length; i++) {
            arrayList.add(new ArrayList());
        }

        for (int j = vertexNum; j >= 1; j--) { //이전의 min함수를 통해 만들어진 arraylist가 increasing order of finish time f[v]이므로 큰 수부터 for문 진행.
            if (!visited[(int) min.get(j)]) {
                ArrayList arrayList0 = new ArrayList(); //방문한 노드들을 저장할 ArrayList arrayList0.
                Collections.sort(aDFS_list_rev((int) min.get(j), visited, ad_list, arrayList0)); //vertices in a line are sorted
                arrayList.remove((int) arrayList0.get(0));
                arrayList.add((Integer) arrayList0.get(0), arrayList0); //arrayList0.get(0)를 통해 arrayList0에서 가장 작은 수에 해당하는 위치에 arraylist를 저장.
            }
        }
        return arrayList;

    }

    static ArrayList aDFS_list_rev(int v, boolean visited[], ArrayList<LinkedList> ad_list, ArrayList arrayList0) {
        visited[v] = true; // 현재 노드를 방문한 것으로 체크
        arrayList0.add(v);  //방문한 노드를 arrayList0에 저장

        Iterator<Integer> it = ad_list.get(v).listIterator(); // 방문한 노드와 인접한 모든 노드를 가지고 온다
        while (it.hasNext()) {
            int n = it.next();
            if (!visited[n]){  // 방문하지 않은 노드면 해당 노드를 다시 시작 노드로하여 aDFS를 호출
                aDFS_list_rev(n, visited, ad_list, arrayList0);
            }
        }

        return arrayList0;
    }

    static int[] dfs_array(int vertexNum, int[] ad_array0, ArrayList ad_array1) {
        boolean[] visited = new boolean[vertexNum + 1]; //각 노드의 방문여부 확인을 위한 배열
        times = new int[vertexNum + 1]; // finish time f [v]
        time = 0; //f [v]에 포함될 각 원소

        for (int j = 1; j < visited.length; j++) {
            if (!visited[j]) { //노드를 방문하지 않았다면,
                aDFS_array(j, visited, ad_array0, ad_array1); ////aDFS 실행
            }
        }
        return times;
    }

    static void aDFS_array(int v, boolean visited[], int[] ad_list0, ArrayList ad_list1) {
        visited[v] = true; // 현재 노드를 방문한 것으로 체크

        if (ad_list0[v - 1] != ad_list0[v]) { //만약 해당 vertex가 자신으로부터 나오는 간선을 가지고 있다면,
            List sublist = new ArrayList<>(ad_list1.subList(ad_list0[v - 1] + 1, ad_list0[v]+1)); // 인접한 모든 노드를 가지고 온다
            Iterator<Integer> it = sublist.listIterator();
            while (it.hasNext()) {
                int n = it.next();
                if (!visited[n]) {
                    aDFS_array(n, visited, ad_list0, ad_list1); // 방문하지 않은 노드면 해당 노드를 다시 시작 노드로하여 aDFS를 호출
                }
            }
        }
        time++;
        times[v] = time;
    }

    static ArrayList<ArrayList> dfs_array_rev(int vertexNum, int[] ad_list0, ArrayList ad_list1, ArrayList min) {
        boolean[] visited = new boolean[vertexNum + 1]; //각 노드의 방문여부 확인을 위한 배열
        ArrayList<ArrayList> arrayList = new ArrayList(); //Run DFS on GR 과정에서 얻은 Output을 저장하기 위한 arraylist<arraylist>.

        for (int i = 0; i < visited.length; i++) {
            arrayList.add(new ArrayList());
        }

        for (int j = vertexNum; j >= 1; j--) { //이전의 min함수를 통해 만들어진 arraylist가 increasing order of finish time f[v]이므로 큰 수부터 for문 진행.
            if (!visited[(int) min.get(j)]) {
                ArrayList arrayList0 = new ArrayList(); //방문한 노드들을 저장할 ArrayList arrayList0.
                Collections.sort(aDFS_array_rev((int) min.get(j), visited, ad_list0, ad_list1, arrayList0)); //vertices in a line are sorted
                arrayList.remove((int) arrayList0.get(0));
                arrayList.add((Integer) arrayList0.get(0), arrayList0); //arrayList0.get(0)를 통해 arrayList0에서 가장 작은 수에 해당하는 위치에 arraylist를 저장.
            }
        }
        return arrayList;
    }

    static ArrayList aDFS_array_rev(int v, boolean visited[], int[] ad_list0, ArrayList ad_list1, ArrayList arrayList0) {
        visited[v] = true; // 현재 노드를 방문한 것으로 체크
        arrayList0.add(v);  //방문한 노드를 arrayList0에 저장

        if (ad_list0[v - 1] != ad_list0[v]) { //만약 해당 vertex가 자신으로부터 나오는 간선을 가지고 있다면,
            List sublist = new ArrayList<>(ad_list1.subList(ad_list0[v - 1] + 1, ad_list0[v]+1));
            Iterator<Integer> it = sublist.listIterator();  // 인접한 모든 노드를 가지고 온다
            while (it.hasNext()) {
                int n = it.next();
                if (!visited[n]){ // 방문하지 않은 노드면 해당 노드를 다시 시작 노드로하여 aDFS를 호출
                    aDFS_array_rev(n, visited, ad_list0, ad_list1, arrayList0);
                }
            }
        }
        return arrayList0;
    }

    static int[][] graph_mat(int vertexNum, ArrayList listInput, String string) {
        if (string.equals("mat")) {  //store the graph into adjacency matrix
            int[][] ad_mat = new int[vertexNum + 1][vertexNum + 1];
            for (int i = 1; i <= vertexNum; i++) {
                String vertex = (String) listInput.get(i); // i번째 vertex에 대한 정보를 listInput[i]에 string형태로 저장했다.
                String[] vertexs = vertex.split(" "); //the (i+ 1)st line contains edges going out of vertex i (the first number in the line is the number of edges).
                //vertexs의 첫번째 원소 이후 원소들은 vertex로부터 나오는 간선을 의미한다.
                    if (!vertex.equals("0")) { //만약 i번째 vertex가 간선을 가진다면,
                        for (int k = 1; k <= Integer.parseInt(vertexs[0]); k++) {
                            ad_mat[i][Integer.parseInt(vertexs[k])] = 1; // ad_mat[i][vertexs[k]]에 1을 저장한다.
                        }
                    }

            }
            return ad_mat;
        } else if (string.equals("matrev")) {  //store the transpose of graph into adjacency matrix.
            int[][] ad_mat_rev = new int[vertexNum + 1][vertexNum + 1];
            for (int i = 1; i <= vertexNum; i++) {
                String vertex = (String) listInput.get(i);
                String[] vertexs = vertex.split(" ");
                    if (!vertex.equals("0")) {
                        for (int k = 1; k <= Integer.parseInt(vertexs[0]); k++) {
                            ad_mat_rev[Integer.parseInt(vertexs[k])][i] = 1; //거의 다 동일하나 여기서는 ad_mat[vertexs[k]][i]에 1을 저장한다.
                        }
                    }

            }
            return ad_mat_rev;
        }
        return new int[0][];
    }

    static ArrayList graph_list(int vertexNum, ArrayList listInput, String string) {
        if (string.equals("list")) { //store the graph into adjacency List
            ArrayList<LinkedList> ad_list = new ArrayList<>();

            ad_list.add(new LinkedList<Integer>());
            for (int i = 1; i <= vertexNum; i++) {
                ad_list.add(new LinkedList<Integer>());
            }

            for (int i = 1; i <= vertexNum; i++) {
                String vertex = (String) listInput.get(i); // i번째 vertex에 대한 정보를 listInput[i]에 string형태로 저장했다.
                String[] vertexs = vertex.split(" "); //the (i+ 1)st line contains edges going out of vertex i (the first number in the line is the number of edges).
                //vertexs의 첫번째 이후 원소들은 vertex로부터 나오는 간선을 의미한다.
                if (!vertex.equals("0")) { //만약 i번째 vertex가 간선을 가진다면,
                    for (int k = 1; k <= Integer.parseInt(vertexs[0]); k++) {
                        ad_list.get(i).add(Integer.parseInt(vertexs[k]));
                    }
                }
            }
            for (int i = 1; i <= vertexNum; i++) {
                Collections.sort(ad_list.get(i)); //각 연결리스트들을 오름차순 정렬한다.
            }
            return ad_list;
        } else if(string.equals("listrev")) {  //store the transpose of graph into adjacency List.
            ArrayList<LinkedList> ad_list_rev = new ArrayList<>();

            ad_list_rev.add(new LinkedList<Integer>());
            for (int i = 1; i <= vertexNum; i++) {
                ad_list_rev.add(new LinkedList<Integer>());
            }

            for (int i = 1; i <= vertexNum; i++) {
                String vertex = (String) listInput.get(i);
                String[] vertexs = vertex.split(" ");
                if (!vertex.equals("0")) {
                    for (int k = 1; k <= Integer.parseInt(vertexs[0]); k++) {
                        ad_list_rev.get(Integer.parseInt(vertexs[k])).add(i);
                    }
                }
            }
            for (int i = 1; i <= vertexNum; i++) {
                Collections.sort(ad_list_rev.get(i)); //각 연결리스트들을 오름차순 정렬한다.
            }
            return ad_list_rev;
        }
        return null;
    }

    static int[] graph_array_list0(int vertexNum, ArrayList listInput, String string) {
        if(string.equals("array")){
            int[] ad_array0 = new int[vertexNum+1]; //end position of vertices adjacent to each vertex in an array는 배열 ad_array0을 사용했다.
            for(int i=1; i<=vertexNum; i++){
                String vertex = (String) listInput.get(i); // i번째 vertex에 대한 정보를 listInput[i]에 string형태로 저장했다.
                String[] vertexs = vertex.split(" ");  //the (i+ 1)st line contains edges going out of vertex i (the first number in the line is the number of edges).
                if (!vertex.equals("0")){
                    ad_array0[i] = ad_array0[i-1]+vertexs.length-1; //ad_array0[i-1]에다가 i번째 vertex가 가진 간선 수를 더해 ad_array0[i]값 저장.
                } else{
                    ad_array0[i] = ad_array0[i-1]; //i번째 vertex가 간선을 가지지 않는다면 ad_array0[i-1]와 ad_array0[i]동일.
                }
            }
            return ad_array0; //한번에 2개의 값을 동시에 return할 수 없으므로 여기서는 ad_array0를 먼저 return한다. 동일한 코드로 ad_array1을 return가능하고,이는 아래의 함수에서 다룰 것이다.
        } else { //store the transpose of graph into adjacency array.
            int[] ad_array0_rev = new int[vertexNum + 1];
            for (int i = 1; i <= vertexNum; i++) {
                String vertex = (String) listInput.get(i);  // i번째 vertex에 대한 정보를 listInput[i]에 string형태로 저장했다.
                String[] vertexs = vertex.split(" ");  //the (i+ 1)st line contains edges going out of vertex i
                if (!vertex.equals("0")) {
                    for (int k = 1; k < vertexs.length; k++) {
                        for (int l = Integer.parseInt(vertexs[k]); l < ad_array0_rev.length; l++) {
                            ad_array0_rev[l]++; //vertexs[k]보다 크거나 같은 모든 원소에 대해 1씩 추가.
                        }
                    }
                }
            }
            return ad_array0_rev; //한번에 2개의 값을 동시에 return할 수 없으므로 여기서는 ad_array0_rev를 먼저 return한다.
        }
    }
    static ArrayList<Integer> graph_array_list1(int vertexNum, ArrayList listInput, String string) { // 여기서는 모든 간선들을 저장하고 있는 arraylist ad_array1를 return한다.
        if(string.equals("array")){
            int[] ad_array0 = new int[vertexNum+1]; //end position of vertices adjacent to each vertex in an array는 배열 ad_array0을 사용했다.
            ArrayList<Integer> ad_array1 = new ArrayList<>(); // 모든 간선들은 arraylist ad_array1에 담았다.
            ad_array1.add(0);
            for(int i=1; i<=vertexNum; i++){
                String vertex = (String) listInput.get(i); // i번째 vertex에 대한 정보를 listInput[i]에 string형태로 저장했다.
                LinkedList<Integer> order = new LinkedList(); //오름차순 정렬을 위한 linked list 정의
                String[] vertexs = vertex.split(" ");  //the (i+ 1)st line contains edges going out of vertex i (the first number in the line is the number of edges).
                if (!vertex.equals("0")) { //만약 i번째 vertex가 간선을 가진다면,
                    for (int k = 1; k <= Integer.parseInt(vertexs[0]); k++) {
                        order.add(Integer.parseInt(vertexs[k])); // 그 간선들을 linked list에 담아
                    }
                    Collections.sort(order); //정렬한다.
                }

                if (!vertex.equals("0")){
                    ad_array0[i] = ad_array0[i-1]+order.size(); //ad_array0[i-1]에다가 i번째 vertex가 가진 간선 수를 더해 ad_array0[i]값 저장.
                    for (int k=0; k<order.size();k++) {
                        ad_array1.add(order.get(k)); //정렬 후 ad_array1에 저장.
                    }
                } else{
                    ad_array0[i] = ad_array0[i-1]; //i번째 vertex가 간선을 가지지 않는다면 ad_array0[i-1]와 ad_array0[i]동일.
                }
            }
            return ad_array1;
        } else {
            int[] ad_array0_rev = new int[vertexNum + 1];
            ArrayList<Integer> ad_array1_rev = new ArrayList<>(1000000);
            ad_array1_rev.add(0);
            for (int i = 1; i <= vertexNum; i++) {
                String vertex = (String) listInput.get(i); // i번째 vertex에 대한 정보를 listInput[i]에 string형태로 저장했다.
                String[] vertexs = vertex.split(" "); //the (i+ 1)st line contains edges going out of vertex i (the first number in the line is the number of edges).

                if (!vertex.equals("0")) {
                    for (int k = 1; k < vertexs.length; k++) {
                        if (ad_array0_rev[Integer.parseInt(vertexs[k])-1] !=  ad_array0_rev[Integer.parseInt(vertexs[k])]){ //vertexs[k]에 해당하는 vertex가 간선을 가지고 있는 경우
                            //다음 for문은 vertexs[k]가 가지는 간선의 오름치순 정렬을 위해 진행하는 것이다.
                            for (int m=ad_array0_rev[Integer.parseInt(vertexs[k])-1] + 1; m<=ad_array0_rev[Integer.parseInt(vertexs[k])]; m++){
                                //vertexs[k]에 해당하는 vertex가 가지는 간선은 ad_array1_rev의 ad_array0_rev[vertexs[k]-1] + 1에서 ad_array0_rev[vertexs[k]] 까지 존재한다.
                                if (i<=ad_array1_rev.get(m)){
                                    ad_array1_rev.add(m,i);
                                    break;
                                } else if(i>ad_array1_rev.get(ad_array0_rev[Integer.parseInt(vertexs[k])])){ //i가 나머지 간선들보다 큰 경우
                                    ad_array1_rev.add(ad_array0_rev[Integer.parseInt(vertexs[k])]+1,i);
                                    break;
                                }
                            }
                        } else{ //vertexs[k]에 해당하는 vertex가 간선을 가지고 있는 경우
                            ad_array1_rev.add(ad_array0_rev[Integer.parseInt(vertexs[k])]+1,i);
                        }
                        //변화된 ad_array1_rev에 맞춰 ad_array0_rev의 원소들도 변화를 준다. vertexs[k]에 해당하는 vertex에 간선이 삽입됨에 따라 ad_array1_rev가 한칸씩 뒤로 밀리므로 이를 고려하여 변경해준다.
                        for (int l = Integer.parseInt(vertexs[k]); l < ad_array0_rev.length; l++) {
                            ad_array0_rev[l]++;
                        }
                    }
                }
            }
            return ad_array1_rev;
        }
    }
}


