import java.io.*;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        String path = Main.class.getResource("").getPath();
        try {
            String read;
            FileReader fr = new FileReader(path + "input1/"+ "input.txt"); //program 1-1: Read an input sequence
            BufferedReader br = new BufferedReader(fr);

            File fileOutput = new File(path+ "input1/"+ "output.txt"); //output.txt에 input sequence를 그대로 print 하고자한다.
            BufferedWriter writerOutput= new BufferedWriter(new FileWriter(fileOutput));

            ArrayList listInput = new ArrayList();
            while ((read = br.readLine()) != null) {
                writerOutput.write(read);  //program 1-2: print it.
                writerOutput.newLine();
                listInput.add(read); //checker program은 input으로 input sequences를 가지므로 미리 저장한다.
            }
            br.close(); // program 1: Read an input sequence and print it. 종료.

            //program 2: Run your program on the input sequence. Print the output sequence. 시작.
            for (int i=0; i<listInput.size(); i++){
                if (listInput.get(i).toString().charAt(0) == 'I'){ //input sequence에서 첫 문자가 "I"인 경우 "OS-Insert" 진행
                    writerOutput.write(Integer.toString(OS_Insert(Integer.parseInt(listInput.get(i).toString().substring(2))))); //결과값 output.txt에 print.
                    writerOutput.newLine();
                } else if(listInput.get(i).toString().charAt(0) == 'D'){ //input sequence에서 첫 문자가 "D"인 경우 "OS-Delete" 진행
                    writerOutput.write(Integer.toString(OS_delete(Integer.parseInt(listInput.get(i).toString().substring(2))))); //결과값 output.txt에 print.
                    writerOutput.newLine();
                } else if(listInput.get(i).toString().charAt(0) == 'S'){ //input sequence에서 첫 문자가 "S"인 경우 "OS-Select" 진행
                    writerOutput.write(Integer.toString(OS_Select(root, Integer.parseInt(listInput.get(i).toString().substring(2))))); //결과값 output.txt에 print.
                    writerOutput.newLine();
                } else { //input sequence에서 첫 문자가 "D"인 경우 "OS-Rank" 진행
                    writerOutput.write(Integer.toString( OS_Rank(findNode(root,Integer.parseInt(listInput.get(i).toString().substring(2)))))); //결과값 output.txt에 print.
                    writerOutput.newLine();
                }
            }
            writerOutput.close();
            //program 2: Run your program on the input sequence. Print the output sequence. 종료.

            //program 3: Check the correctness of your program by a checker program. Print the result of checking. 시작.
            FileReader outputTxt = new FileReader(path + "input1/"+ "output.txt");
            BufferedReader txtReader = new BufferedReader(outputTxt);

            ArrayList listOutput = new ArrayList();
            while ((read = txtReader.readLine()) != null) {
                listOutput.add(read); //checker program은 input으로 input and output sequences을 가지므로 output.txt에 저장된 정보들을 읽어오고자 한다.
            }
            txtReader.close();

            File fileOutput2 = new File(path + "input1/"+ "checker.txt"); //checker program을 돌려 프로그램 결과가 맞는지 output.txt에 출력하고자한다.
            BufferedWriter writerOutput2= new BufferedWriter(new FileWriter(fileOutput2));
            writerOutput2.write(String.valueOf(checkerProgram(listInput,listOutput)));  //program 3: Check the correctness of your program by a checker program. Print the result of checking
            writerOutput2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // order-statistic tree 구성을 위해 먼저, node를 define하고자 한다.
    private static final int BLACK = 0; //red-black tree를 기반으로 가지므로 색을 int로 정의한다.
    private static final int RED = 1;

    private static Node root; //각 tree는 루트 값을 가진다. 또한 루트에 의해 모든 tree에 대한 정보를 가지고 올 수 있다.

    private static class Node {
        private int value;
        private int color;
        private int size; //red-black tree에서 size variable 추가

        Node left;
        Node right;
        Node parent;

        Node(int value) {
            this.value = value;
            color = BLACK;
            size = 1;

            left = null;
            right = null;
            parent = null;
        }

        int getValue() {
            return value;
        }

        int getSize() {
            return size;
        }

        String getColor() {
            return color == RED ? "RED" : "BLACK";
        }

        void setColor(int color) {
            this.color = color;
        }

    }

    public static int OS_Insert(int x) {
        Node node = new Node(x); //삽입할 노드 정의
        if(root == null) {
            node.setColor(BLACK); // case1: 루트가 없는 경우, 검은색 루트 노드 삽입
            root = node;
            return root.value;
        } else {
            if (findNode(root, node.value)!=null){ //if, x is already inserted; return 0.
                return 0;
            }
            node.setColor(RED);  //case2: 루트 노드가 있는 경우, 빨간색 리프 노드 삽입
            Node parent = root; // node의 저장을 위해 루트 노드부터 아래로 내려갈 것이다.
            while(true) {
                // 노드의 값이 부모보다 크다면 계속 오른쪽 자식을 부모로 접근
                if(node.getValue() > parent.getValue()) {
                    if(parent.right == null) { // 부모의 오른쪽 노드 자리가 비었다면, 오른쪽 노드가 된다.
                        parent.right = node;
                        node.parent = parent;
                        parent.size++; // 부모노드의 size를 1 증가시킨다.
                        break;
                    } else {
                        parent.size++; //오른쪽 노드자리가 비어있지 않다면 트리 아래로 더 타고 내려간다. 이때 부모노드의 size를 1 증가시킨다.
                        parent = parent.right;
                    }
                } else {
                    if(parent.left == null) { // 부모의 왼쪽 노드 자리가 비었다면, 왼쪽 노드가 된다.
                        parent.left = node;
                        node.parent = parent;
                        parent.size++; //부모노드의 size를 1 증가시킨다.
                        break;
                    } else {
                        parent.size++; //왼쪽 노드자리가 비어있지 않다면 트리 아래로 더 타고 내려간다. 이때 부모노드의 size를 1 증가시킨다.
                        parent = parent.left;

                    }
                }
            }
            int k =node.value;
            //삽입 완료. 이제 색 변환 과정.

            // case A: 만약 부모 노드의 색이 검정색이라면 여기서 삽입과정 종료.
            if(node.parent.getColor().equals("RED")){ // case B: 현재 아는 것, 삽입 노드와 부모 노드 빨간색. 부모 노드의 부모노드는 검정색.
                while (node.parent != null && node.parent.getColor().equals("RED")) {
                    if(node.parent == node.parent.parent.left) { //부모노드가 부모노드의 부모노드의 왼쪽에 위치한 경우
                        Node otherParent  = node.parent.parent.right;  //형제노드는 부모노드의 부모노드의 오른쪽에 위치할 것임.
                        if(otherParent != null && otherParent.getColor().equals("RED")) { //case B-1: 부모노드의 형제노드 색이 빨간색인 경우
                            node.parent.setColor(BLACK);
                            otherParent.setColor(BLACK);
                            node.parent.parent.setColor(RED);
                            node = node.parent.parent;
                        } else { //case B-2: 부모노드의 형제노드 색이 검은색인 경우이거나 존재하지 않는 경우
                            if(node == node.parent.right) { //현재 노드가 부모 노드의 우측에 있다면 "부모 노드"를 중심으로 left rotate.
                                rotateLeft(node.parent);
                                node.setColor(BLACK);
                                node.parent.setColor(RED);
                                rotateRight(node.parent);
                            } else{ //현재 노드가 부모 노드의 좌측에 있다면 "부모 노드의 부모 노드"를 중심으로 right rotate.
                                node.parent.setColor(BLACK);
                                node.parent.parent.setColor(RED);
                                rotateRight(node.parent.parent);
                            }
                            break;
                        }
                    }  else { // 이제 반대로 부모노드가 부모노드의 부모노드의 오른쪽에 위치한 경우이다. 위의 경우와 방법은 동일하며 방향만 반대이다.
                        Node otherParent  = node.parent.parent.left;// 부모 노드의 형제는 부모 노드의 부모 노드의 왼쪽 노드이다.
                        if(otherParent != null && otherParent.getColor().equals("RED")) { //case B-3: 부모노드의 형제노드 색이 빨간색인 경우
                            node.parent.setColor(BLACK); //부모노드의 색과 부모노드의 형제노드 색을 검정색으로 바꾼다.
                            otherParent.setColor(BLACK);
                            node.parent.parent.setColor(RED); //부모노드의 부모노드 색을 빨간색으로 바꾼다.
                            node = node.parent.parent; //부모노드의 부모노드에 문제가 발생한다면 while을 통해 재귀적으로 다시 시작한다.
                        } else { //case B-4: 부모노드의 형제노드 색이 검은색인 경우이거나 존재하지 않는 경우
                            if(node == node.parent.left) { //현재 노드가 부모 노드의 좌측에 있다면 "부모 노드"를 중심으로 right rotate 후 "부모 노드"를 중심으로 left rotate.
                                rotateRight(node.parent);
                                node.setColor(BLACK);
                                node.parent.setColor(RED);
                                rotateLeft(node.parent);
                            } else{ //현재 노드가 부모 노드의 우측에 있다면 "부모 노드의 부모 노드"를 중심으로 left rotate.
                                node.parent.setColor(BLACK);
                                node.parent.parent.setColor(RED);
                                rotateLeft(node.parent.parent);
                            }
                            break;
                        }
                    }
                }
            }
            root.setColor(BLACK); //마지막으로 루트노드는 항상 검정색이여야하므로 검정색으로 칠해준다.
            return k; //returns x, if integer x is not already in order-statistic tree T
        }
    }

    public static int OS_delete(int x){
        if (findNode(root,x) == null){ //우선 삭제할 노드가 tree 내에 위치하는지부터 체크하고, 존재하지 않으면 0을 return한다.
            return 0;
        } else {
            Node node = findNode(root,x); // 삭제할 노드가 tree내에 위치하는 경우를 다룬다.
            int value = node.value;
            if (node.left != null && node.right != null){ // 삭제될 노드의 자식이 둘인 경우. 삭제될 노드의 자식이 둘인 경우 -> 삭제될 노드의 자식이 하나인 경우로만 생각하면 됨.
                Node minNode = findMinimum(node.right); //삭제될 노드의 오른쪽 서브트리에서 value가 가장 작은 node = minNode를 찾는다.
                Node repairNode = makeRepairNode(minNode); //minNode를 삭제될 노드 위치로 옮기기 전, red-black 특성에 문제생긴 경우 tree의 구조를 바꾸는 과정을 더 쉽게하기 위해, minNode와 동일한 부모, 자식, value, color, size를 가지는 노드를 하나 만둘어둔다.
                connection0(node, minNode); // minNode를 삭제될 노드 위치로 옮긴다.
                minNode.size = node.size;
                node.value = -1; // 노드 삭제를 value를 -1로 만드는 것으로 진행할 것이다.
                deleteNode(repairNode); //기존의  minNode 위치에 있던 노드를 삭제하는 과정을 진행한다.
                repairNode.value = -1; // 노드 삭제를 value를 -1로 만드는 것으로 진행할 것이다.
            } else{ // 삭제될 노드의 자식이 없거나 하나인 경우
                deleteNode(node);
                node.value = -1; // 노드 삭제를 value를 -1로 만드는 것으로 진행할 것이다.
            }
            return value;
        }
    }

    private static void deleteNode(Node node){
        if (node.color == RED){ // case1: 삭제할 노드가 빨간색인 경우.
            if (node.left == null && node.right == null){ // case 1-1: 삭제할 노드의 자식이 없는 경우
                if (node.parent.left == node){
                    node.parent.left = null;
                } else{
                    node.parent.right = null;
                }
                sizeDown(node); // 삭제할 노드의 부모노드는 여전히 변하지 않았다. 부모노드를 타고 올라가면서 size 하나씩 감소시킨다.
            } else if(node.left == null){ // 삭제될 노드의 자식이 하나인 경우. 자식노드를 삭제할 노드의 위치로 옮긴다.(색은 변하지 않는다.)
                connection(node, node.right);
                node.right.color = BLACK; //삭제할 노드가 빨간색이므로, 자식노드는 검정색임이 분명하다.
                sizeDown(node);  // 삭제할 노드의 부모노드는 여전히 동일하다. 부모노드를 타고 올라가면서 size 하나씩 감소시킨다.
            } else if(node.right == null){
                connection(node, node.left);
                node.left.color = BLACK; //삭제할 노드가 빨간색이므로, 자식노드는 검정색임이 분명하다.
                sizeDown(node);  // 삭제할 노드의 부모노드는 여전히 동일하다. 부모노드를 타고 올라가면서 size 하나씩 감소시킨다.
            }
        } else if(node.color == BLACK && (node.right != null || node.left != null)){ //case2: 삭제할 노드가 검정색이고, 자식노드가 존재하는 경우.
            if (node == root){ //case 2-0: 삭제할 노드가 루트노드인 경우
                if(node.right == null){
                    node.left.parent = null;
                    root = node.left;
                } else {
                    node.right.parent = null;
                    root = node.right;
                }
                //이 경우에는 size의 변화가 없다.
            } else if(node.left == null && node.right.color == RED) { //case2-1-1 : 삭제할 노드가 검정색, 유일한 자식노드가 오른쪽에 위치하고 빨간 색.
                connection(node, node.right);
                sizeDown(node); // 삭제할 노드의 부모노드는 여전히 변하지 않았다. 그러나 더이상 부모노드의 자식노드가 아니긴 하다.
            } else if (node.right == null && node.left.color == RED){ //case2-1-2 : 삭제할 노드가 검정색, 유일한 자식노드가 왼쪽에 위치하고 빨간 색.
                connection(node, node.left);
                sizeDown(node); // 삭제할 노드의 부모노드는 여전히 변하지 않았다. 그러나 더이상 부모노드의 자식노드가 아니긴 하다.
            } else { //case2-2: 삭제할 노드가 검정색이고, 유일한 자식 노드가 검정색인 경우. 검정색 노드가 삭제됨에 따라, 이 경우, 루트 노드로부터 리프노드까지 가면서 만나는 검정색 노드의 수가 달라지게 되어 트리 구조에 변화를 주어야한다.
                if(node.right == null){ //case2-2-1 : 삭제할 노드가 검정색, 유일한 자식노드가 왼쪽에 위치하고 검정색
                    connection(node, node.left);
                    sizeDown(node);
                    repairNode(node.left); // 트리 구조에 변화를 주는 과정을 진행한다.
                } else{ //case2-2-2 : 삭제할 노드가 검정색, 유일한 자식노드가 오른쪽에 위치하고 검정색
                    connection(node, node.right);
                    sizeDown(node);
                    repairNode(node.right); // 트리 구조에 변화를 주는 과정을 진행한다.
                }
            }
        }else { //case3: 삭제할 노드가 검정색이고, 자식노드가 존재하지 않는 경우
            if (node == root){// 삭제할 노드가 루트노드인 경우
                root = null;
            } else if(node.left == null && node.right == null ) {  //case3-1: 삭제할 노드가 검정색이고, 자식노드가 존재하지 않는 경우
                node.size = 0;
                node.value = -2; //임의의 NIL NODE를 가정하며, 이미 부모 노드와 NIL 노드와 연결되어있다고 생각할 수 있다.

                sizeDown(node);
                repairNode(node); // 트리 구조에 변화를 주는 과정을 진행한다.

                if(node.parent.left == node){
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
                node.value = -1; // 앞서 정의한 임의의 NIL NODE를 tree로부터 제거하는 과정을 진행한다.
            }
        }
    }

    public static Node makeRepairNode (Node node){ // 삭제될 노드가 자식이 둘인 경우 이후의 오른쪽 서브트리에서 가장 큰 밸류를 가진 노드를 가져오는데, 트리의 재구조화의 편의성을 위해 동일한 노드를 미리 하나 만들어 두었다.
        Node repairNode = new Node(-1);
        Node minNode = findMinimum(node);
        repairNode.size = minNode.size;
        repairNode.value = minNode.value;
        repairNode.left = minNode.left;
        repairNode.right = minNode.right;
        repairNode.parent = minNode.parent;
        repairNode.color = minNode.color;
        if (minNode.parent.left == minNode){
            minNode.parent.left = repairNode;
        } else {
            minNode.parent.right = repairNode;
        }
        if (minNode.right != null){
            minNode.right.parent = repairNode;
        }
        return repairNode;
    }


    public static void sizeDown(Node node){
        while (node.parent != null){ // node가 root에 도달하면 종료.
            node.parent.size = node.parent.size-1; //하나씩 올라가면서, 만나는 노드마다 size를 하나씩 줄인다.
            node = node.parent;
        }
    }

    public static void connection(Node node, Node newNode){ // 삭제할 노드의 자식이 하나인 경우, 삭제할 노드의 위치로 자식 노드가 이동하게 해주는 함수다.
        if (node.parent != null){
            if (node.parent.left == node){
                node.parent.left = newNode;
            } else if (node.parent.right == node){
                node.parent.right = newNode;
            }
        }
        newNode.parent = node.parent; //삭제할 노드의 부모의 자식노드로 새로운 노드를 연결.
        newNode.setColor(node.color);
    }

    public static void connection0(Node node, Node newNode){ // 삭제할 노드의 자식이 둘인 경우, (오른쪽 서브트리에서 value가 가장 작은 노드)가 삭제할 노드의 위치로 이동해야할 때 사용하는 함수다.
        if (node != root){
            if (node.parent.left == node){
                node.parent.left = newNode; //삭제할 노드의 부모의 왼쪽 자식으로 newNode 연결.
            } else if (node.parent.right == node){
                node.parent.right = newNode; //삭제할 노드의 부모의 오른쪽 자식으로 newNode 연결.
            }
            if (node.left != null){
                node.left.parent = newNode;
                newNode.left = node.left; //삭제할 노드의 왼쪽 자식노드의 부모노드로 newNode 연결.
            }
            if (node.right != null){
                if (node.right !=  newNode) {
                    node.right.parent = newNode;
                    newNode.right = node.right; //삭제할 노드의 오른쪽 자식노드의 부모노드로 newNode 연결.
                }
            }
            newNode.parent = node.parent;
            newNode.setColor(node.color);
        } else{ //만약 삭제되는 노드가 루트노드라면, 특별히 다른 과정을 거친다.
           //루트노드는 부모노드를 갖지 않으므로 생략한다.
            root = newNode;
            root.right = node.right;
            node.right.parent = root;

            root.left = node.left;
            node.left.parent = root;

            root.color = BLACK; //루트노드는 검정색이다.
            root.parent = null;
        }
    }

    public static void repairNode(Node node){ //쉽게 배우는 알고리즘 - 문병로 저를 동일하게 구현하였다.
        if (node.parent != null){
            if (node.parent.left == node){ // 기준 노드가 부모로부터 좌측에 있는 경우.
                if (node.parent.right == null){ //caseA: 형제노드가 존재하지 않는 경우.
                    if (node.parent.color== RED){ //case 1-1.
                        node.parent.setColor(BLACK);
                    }else{ //case 2-1.
                        repairNode(node.parent);
                    }
                } else if(node.parent.right.left == null) { //caseB: 형제노드의 왼쪽 노드가 존재하지 않는 경우.
                    if (node.parent.color == RED && node.parent.right.color == BLACK
                            && (node.parent.right.right == null || node.parent.right.right.color == BLACK)){ //case 1-1.
                        node.parent.right.setColor(RED);
                        node.parent.setColor(BLACK);
                    }  else
                    if(node.parent.color == BLACK && node.parent.right.color == BLACK
                            && (node.parent.right.right == null || node.parent.right.right.color == BLACK)){ //case 2-1.
                        node.parent.right.setColor(RED);
                        repairNode(node.parent);
                    } else if (node.parent.color == BLACK && node.parent.right.color == RED
                            && (node.parent.right.right == null || node.parent.right.right.color == BLACK)){ //case 2-4.
                        node.parent.right.setColor(BLACK);
                        node.parent.setColor(RED);
                        rotateLeft(node.parent);
                        repairNode(node);
                    }   else if(node.parent.right.color == BLACK && node.parent.right.right.color == RED) { //case *-2.
                        node.parent.right.setColor(node.parent.color);
                        node.parent.setColor(BLACK);
                        node.parent.right.right.setColor(BLACK);
                        rotateLeft(node.parent);
                    }
                    //caseC: 형제노드의 왼쪽 노드도 존재하는 경우.
                } else if (node.parent.color == RED && node.parent.right.color == BLACK && node.parent.right.left.color == BLACK
                        && (node.parent.right.right == null || node.parent.right.right.color == BLACK)){ //case 1-1.
                    node.parent.right.setColor(RED);
                    node.parent.setColor(BLACK);
                }  else if(node.parent.color == BLACK && node.parent.right.color == BLACK && node.parent.right.left.color == BLACK
                        && (node.parent.right.right == null || node.parent.right.right.color == BLACK)){ //case 2-1.
                    node.parent.right.setColor(RED);
                    repairNode(node.parent);
                } else if (node.parent.color == BLACK && node.parent.right.color == RED && node.parent.right.left.color == BLACK
                        && (node.parent.right.right == null || node.parent.right.right.color == BLACK)){ //case 2-4.
                    node.parent.right.setColor(BLACK);
                    node.parent.setColor(RED);
                    rotateLeft(node.parent);
                    repairNode(node);
                }  else if (node.parent.right.color == BLACK && node.parent.right.left.color == RED
                        && (node.parent.right.right == null || node.parent.right.right.color== BLACK) ){ //case *-3
                    node.parent.right.setColor(RED);
                    node.parent.right.left.setColor(BLACK);
                    rotateRight(node.parent.right);
                    repairNode(node);
                } else if(node.parent.right.color == BLACK && node.parent.right.right.color == RED
                        && (node.parent.right.left.color == RED || node.parent.right.left.color == BLACK)) { //case *-2
                    node.parent.right.setColor(node.parent.color);
                    node.parent.setColor(BLACK);
                    node.parent.right.right.setColor(BLACK);
                    rotateLeft(node.parent);
                }
            } else { // 기준 노드가 부모로부터 우측에 있는 경우이며, 위의 과정과 방법은 동일하며, 방향만 반대다.
                if (node.parent.left == null){ //caseA: 형제노드가 존재하지 않는 경우.
                    if (node.parent.color== RED){ //case 1-1.
                        node.parent.setColor(BLACK);
                    }else{ //case 2-1.
                        repairNode(node.parent);
                    }
                } else if(node.parent.left.right == null){
                    if (node.parent.color == RED && node.parent.left.color == BLACK
                            && (node.parent.left.left == null || node.parent.left.left.color == BLACK)){ //case 1-1.
                        node.parent.left.setColor(RED);
                        node.parent.setColor(BLACK);
                    } else if(node.parent.color == BLACK && node.parent.left.color == BLACK
                            && (node.parent.left.left == null || node.parent.left.left.color == BLACK)){ //case 2-1.
                        node.parent.left.setColor(RED);
                        repairNode(node.parent);
                    } else if (node.parent.color == BLACK && node.parent.left.color == RED
                            && (node.parent.left.left == null || node.parent.left.left.color == BLACK)){ //case 2-4.
                        node.parent.left.setColor(BLACK);
                        node.parent.setColor(RED);
                        rotateRight(node.parent);
                        repairNode(node);
                    }  else if(node.parent.left.color == BLACK && node.parent.left.left.color == RED) { //case *-2.
                        node.parent.left.setColor(node.parent.color);
                        node.parent.setColor(BLACK);
                        node.parent.left.left.setColor(BLACK);
                        rotateRight(node.parent);
                    }
                } else if (node.parent.color == RED && node.parent.left.color == BLACK && node.parent.left.right.color == BLACK
                        && (node.parent.left.left == null || node.parent.left.left.color == BLACK)){ //case 1-1.
                    node.parent.left.setColor(RED);
                    node.parent.setColor(BLACK);
                }  else if(node.parent.color == BLACK && node.parent.left.color == BLACK && node.parent.left.right.color == BLACK
                        && (node.parent.left.left == null || node.parent.left.left.color == BLACK)){ //case 2-1.
                    node.parent.left.setColor(RED);
                    repairNode(node.parent);
                } else if (node.parent.color == BLACK && node.parent.left.color == RED && node.parent.left.right.color == BLACK
                        && (node.parent.left.left == null || node.parent.left.left.color == BLACK)){ //case 2-4.
                    node.parent.left.setColor(BLACK);
                    node.parent.setColor(RED);
                    rotateRight(node.parent);
                    repairNode(node);
                }  else if (node.parent.left.color == BLACK  && node.parent.left.right.color == RED
                        && (node.parent.left.left == null || node.parent.left.left.color== BLACK )){ //case *-3
                    node.parent.left.setColor(RED);
                    node.parent.left.right.setColor(BLACK);
                    rotateLeft(node.parent.left);
                    repairNode(node);
                } else if(node.parent.left.color == BLACK && node.parent.left.left.color == RED
                        && (node.parent.left.right.color == RED || node.parent.left.right.color == BLACK)) { //case *-2
                    node.parent.left.setColor(node.parent.color);
                    node.parent.setColor(BLACK);
                    node.parent.left.left.setColor(BLACK);
                    rotateRight(node.parent);
                }
            }
        } else {
            root.color = BLACK;
        }

    }

    private static Node findMinimum(Node node){ //삭제할 노드의 오른쪽 서브트리에서 가장 작은 value를 가지는 노드를 찾는 함수다.
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static int OS_Select(Node node, int i){
        if(root.size < i){ //만약 트리가 가지는 노드의 수보다 i가 크면 0을 return한다.
            return 0;
        } else {
            int r;
            if (node.left== null){ //만약 노드가 왼쪽 노드를 가지고 있지 않다면, r=1로 설정한다.
                r=1;
            } else{
                r = node.left.size+1;
            }
            //나머지 부분은 강의자료와 동일하다.
            if (i == r){
                return node.value;
            } else if(i<r){
                return OS_Select(node.left,i);
            } else {
                return OS_Select(node.right,i-r);
            }
        }
    }

    public static int OS_Rank(Node x){
        if (x == null){
            return 0;
        } //x를 value로 가지는 노드가 존재하지 않는다면 0을 return한다.
        int r;
        if (x.left == null){
            r = 1; //만약 x를 value로 가지는 노드가 왼쪽 노드를 가지고 있지 않다면, r=1로 설정한다.
        } else {
            r = x.left.size+1;
        }
        //나머지 부분은 강의자료와 동일하다.
        Node y = x;
        while (y != root){
            if (y == y.parent.right) {
                if (y.parent.left == null){
                    r= r+1;
                } else{
                    r = r + y.parent.left.size + 1;
                }
            }
            y = y.parent;
        }
        return r;

    }

    private static void rotateLeft(Node node) { //좌회전
        if(node.parent == null) { // 기준 노드가 루트 노드인 경우
            Node right = root.right;
            right.parent = null;

            root.parent = right;

            if(right.left != null) { //만약 (기준 노드의 오른쪽 노드)가 왼쪽 노드를 가지고 있었다면, 이것이 기준노드의 오른쪽 노드가 된다.
                right.left.parent = root;
                root.right = right.left;
            } else{
                node.right = null;
            }

            right.left = root;
            root = right;

            root.size = root.left.size;
            if (root.left.left == null && root.left.right == null){
                root.left.size = 1;
            } else if(root.left.left == null){
                root.left.size = root.left.right.size+1;
            } else if(root.left.right == null){
                root.left.size = root.left.left.size+1;
            } else{
                root.left.size = root.left.left.size+ root.left.right.size+1;
            }
        }

        else { // 기준 노드의 부모노드가 존재하는 경우
            if(node == node.parent.left) { //기준 노드가 부모 노드의 왼쪽에 위치한다면, (기준 노드의 오른쪽 노드)가, (기준 노드의 부모노드의 왼쪽 노드)가 됨.
                node.parent.left = node.right;
                node.right.parent = node.parent;
            }
            else { // 반대의 경우도 마찬가지임.
                node.parent.right = node.right;
                node.right.parent = node.parent;
            }

            node.parent = node.right; //left rotate를 하면, 기준 노드의 오른쪽 노드가 기준 노드의 부모노드가 된다.

            if(node.right.left != null) { //만약 (기준 노드의 오른쪽 노드)가 왼쪽 노드를 가지고 있었다면, 이것이 기준노드의 오른쪽 노드가 된다.
                node.right.left.parent = node;
                node.right = node.right.left;
            } else {
                node.right = null;
            }

            node.parent.left = node; //마지막으로,기준 노드가 새롭게 자신의 부모노드가 된 (초기 상태에서 기준노드의 오른쪽 노드)의 왼쪽 노드가 된다.

            //size 설정 과정.
            node.parent.size = node.size; // (초기상태의 기준노드의 오른쪽 노드)가 기준노드의 자리를 차지하면서 size도 물려받는다.
            if (node.left == null && node.right == null){
                node.size =1;
            } else if(node.left == null){
                node.size = node.right.size+1;
            } else if (node.right == null){
                node.size = node.left.size+1;
            } else{
                node.size = node.left.size+node.right.size+1;
            }
            // 기준노드가 이동하면서, size는 이동한 위치에서의 자식노드의 size의 합+1이 된다.
        }
    }

    private static void rotateRight(Node node) { //우회전
        if(node.parent == null) { // 기준 노드가 루트 노드인 경우
            Node left = root.left;
            left.parent = null;

            root.parent = left;

            if(left.right != null) {
                left.right.parent = root;
                root.left = left.right;
            } else{
                root.left = null;
            }

            left.right = root;
            root =left;

            root.size = root.right.size;
            if (root.right.left == null && root.right.right == null){
                root.right.size = 1;
            } else if(root.right.left == null){
                root.right.size = root.right.right.size+1;
            } else if(root.right.right == null){
                root.right.size = root.right.left.size+1;
            } else{
                root.right.size = root.right.left.size+ root.right.right.size+1;
            }
        }
        else { // 기준 노드의 부모노드가 존재하는 경우
            if(node == node.parent.left) { //기준 노드가 부모 노드의 왼쪽에 위치한다면, (기준 노드의 왼쪽 노드)가, (기준 노드의 부모노드의 왼쪽 노드)가 됨.
                node.parent.left = node.left;
                node.left.parent = node.parent;
            }
            else { // 반대의 경우도 마찬가지임.
                node.parent.right = node.left;
                node.left.parent = node.parent;
            }

            node.parent = node.left; //right rotate를 하면, 기준 노드의 왼쪽 노드가 기준 노드의 부모노드가 된다.

            if(node.left.right != null) { //만약 (기준 노드의 왼쪽 노드)가 오른쪽 노드를 가지고 있었다면, 이것이 기준노드의 왼쪽 노드가 된다.
                node.left.right.parent = node;
                node.left = node.left.right;
            } else{
                node.left = null;
            }

            node.parent.right = node; //마지막으로,기준 노드가 새롭게 자신의 부모노드가 된 (초기 상태에서 기준노드의 왼쪽 노드)의 오른쪽 노드가 된다.

            //size 설정 과정.
            node.parent.size = node.size; // (초기상태의 기준노드의 왼쪽 노드)가 기준노드의 자리를 차지하면서 size도 물려받는다.
            if (node.left == null && node.right == null){
                node.size =1;
            } else if(node.left == null){
                node.size = node.right.size+1;
            } else if (node.right == null){
                node.size = node.left.size+1;
            } else{
                node.size = node.left.size + node.right.size+1;
            } // 기준노드가 이동하면서, size는 이동한 위치에서의 자식노드의 size의 합+1이 된다.
        }
    }
    private static Node findNode(Node node, int x) { //x를 value로 가지는 노드를 찾는 함수다.
        while (true) {
            if (x > node.value) {
                if (node.right == null) {
                    return null;
                } else {
                    node = node.right;
                }
            } else if(x < node.value){
                if (node.left == null) {
                    return null;
                } else {
                    node = node.left;
                }
            } else{
                return node;
            }
        }
    }

    private static boolean checkerProgram(ArrayList input, ArrayList output ){ // A checker program gets the input and output sequences as its input
        for (int i = 0; i<input.size(); i++){
            if (!input.get(i).equals(output.get(i))){
                return false;
            }
        } // program 1: 에서 input을 read 한 후 이를 output.txt파일에 print 하므로 먼저 그 부분에 대해 동일한지 체크하는 것이다.

        int[] arr = new int[10000]; //array 정의. 만약 array[i] = -1이라면, i를 value로 가지는 노드가 tree에 존재하지 않는다는 의미이며, a[i] = i 이면 i를 value로 가지는 노드가 tree에 존재하지 않는다는 의미라고 생각할 것이다.

        arr[0] = -9999;  //정의한 array에서 array[0]은 사용안할 것이다.

        for (int i=1; i<10000; i++){ // Write a checker program by using an array A[1..9999].
            arr[i] = -1;
        }

        //이제 프로그램을 돌린 후의 결과값이 맞는 지 확인해보고자 한다.
        for (int i = 0; i<input.size(); i++){
            if (input.get(i).toString().charAt(0) == 'I'){
                if (arr[Integer.parseInt(input.get(i).toString().substring(2))] == Integer.parseInt(input.get(i).toString().substring(2))){ //a[i] = i
                    if (!output.get(i+input.size()).toString().equals("0")){ //이 경우, 이미 tree에 i를 value로 가지는 노드가 tree에 존재하므로, 프로그램이 0을 return하는 것이 참이다.
                        return false;
                    }
                } else { //a[i] = -1
                    arr[Integer.parseInt(input.get(i).toString().substring(2))] = Integer.parseInt(input.get(i).toString().substring(2)); //array에 저장.
                    if (!output.get(i+input.size()).toString().equals(input.get(i).toString().substring(2))){ //이 경우, tree에 i를 value로 가지는 노드가 tree에 존재하지 않으므로, 프로그램이 i이 return하는 것이 참이다.
                        return false;
                    }
                }
            } else if(input.get(i).toString().charAt(0) == 'D'){
                if (arr[Integer.parseInt(input.get(i).toString().substring(2))] == -1){ //a[i] = -1
                    if (!output.get(i+input.size()).toString().equals("0")){ //이 경우, tree에 i를 value로 가지는 노드가 tree에 존재하지 않으므로, 프로그램이 i이 return하는 것이 참이다.
                        return false;
                    }
                } else {
                    arr[Integer.parseInt(input.get(i).toString().substring(2))] = -1; //array에서 삭제.
                    if (!output.get(i+input.size()).toString().equals(input.get(i).toString().substring(2))){  //이 경우, 이미 tree에 i를 value로 가지는 노드가 tree에 존재하므로, 프로그램이 i을 return하는 것이 참이다.
                        return false;
                    }
                }
            } else if(input.get(i).toString().charAt(0) == 'S'){
                int sum=0;
                for (int k=1; k<arr.length; k++){
                    if(arr[k]>=0){
                        sum++;
                    }
                } //먼저 tree가 가지고 있는 노드의 수를 먼저 체크한다.
                if (sum< Integer.parseInt(input.get(i).toString().substring(2))){
                    if (Integer.parseInt(output.get(i+input.size()).toString()) != 0 ){
                        return false; //만약, tree가 가지고 있는 노드의 수보다, input이 더 크다면 output은 0이 return되는 것이 참이다.
                    }
                } else {
                    sum = 0;
                    for (int j=1; j<=Integer.parseInt(output.get(i+input.size()).toString());j++){
                        if (arr[j]>=0){
                            sum++;
                        }
                    } //output값이 array에서 몇번째로 큰지 확인한다.
                    if (sum != Integer.parseInt(input.get(i).toString().substring(2)) ){
                        return false;
                    } // 이 값(몇번째로 큰지)이 input과 다르다면 checker program은 false를 return 해야한다.

                }
            } else {
                if (arr[Integer.parseInt(input.get(i).toString().substring(2))] != Integer.parseInt(input.get(i).toString().substring(2))){
                    if (!output.get(i+input.size()).toString().equals("0")){
                        return false;
                    } //우선 x를 value로 가지는 노드가 존재하지 않는다면 "프로그램"은 0을 return해야한다.
                } else{
                    int sum2=0;
                    for (int j=1; j<=Integer.parseInt(input.get(i).toString().substring(2));j++){
                        if (arr[j]>=0){
                            sum2++;
                        }
                    } //input값이 array에서 몇번째로 큰지 확인한다.
                    if (sum2 != Integer.parseInt(output.get(i+input.size()).toString()) ){
                        return false;
                    } //만약, 그 값이 output값과 동일하지 않다면 checker program은 false를 return 해야한다.
                }
            }
        }
        return true;
    }
}
