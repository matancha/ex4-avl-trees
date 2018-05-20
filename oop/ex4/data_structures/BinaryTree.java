package ex4.data_structures;

import java.util.Iterator;

public class BinaryTree {
    protected TreeNode root;
    protected int size;
    BinaryTree(int rootData){
        this.root=new TreeNode(rootData,0);
        this.size=1;
    }
    protected class TreeNode{
        protected int nodeData;
        protected TreeNode leftSon;
        protected TreeNode rightson;
        protected int depth;
        TreeNode(int nodeData,int depth){
            this.depth=depth;
            this.nodeData=nodeData;
            this.leftSon=null;
            this.rightson=null;
        }
        public int getNumberOfSuns(){
            int counter=0;
            if (rightson!=null){
                counter++;
            }if (leftSon!=null){
                counter++;
            }
            return counter;
        }

    }
//    protected Iterator<Integer> iterator(){
//        class TreeIterator implements Iterator<Integer>{
//            private TreeNode current;
//            TreeIterator(){
//                current=root;
//            }public boolean hasNext(){
//                if (){
//                    return next;
//                }
//            }Integer next(){
//                if (current.leftSon!=null){
//                    current=current.leftSon;
//                    return next();
//                }if (current.rightson!=null){
//                    return next();
//                }
//            }
//        }
//    }
    public boolean add(int newValue){
        if (contains(newValue)==-1){
            size++;
            return addHelper(newValue,root);
        }
        return false;
    }
    private boolean addHelper(int newValue,TreeNode currentNode){
        if (currentNode.nodeData>newValue){
            if (currentNode.leftSon!=null){
                return addHelper(newValue,currentNode.leftSon);
            }else{
                currentNode.leftSon=new TreeNode(newValue,currentNode.depth+1);
            }
        }else if(currentNode.nodeData<newValue){
            if (currentNode.rightson!=null){
                return addHelper(newValue,currentNode.rightson);
            }else{
                currentNode.rightson=new TreeNode(newValue,currentNode.depth+1);
            }
        }
        return true;
    }

    public int contains(int searchVal){
        if (searchVal==root.nodeData){
            return 0;
        }
        TreeNode foundedBranching=findBranching(searchVal);
        if (foundedBranching!=null){
            return foundedBranching.depth+1;
        }
        return -1;
    }public int size(){
        return size;
    }
    public boolean delete(int toDelete){
        TreeNode foundedBranching=findBranching(toDelete);
        if (foundedBranching!=null){
            TreeNode foundedNode=determineSon(foundedBranching,toDelete);
            if (foundedNode==null){
                foundedNode=foundedBranching;
            }
            switch (foundedNode.getNumberOfSuns()){
                case 0:
                    zeroSonsCase(foundedBranching,foundedNode);
                    break;
                case 1:
                    oneSonCase(foundedBranching,foundedNode);
                    break;
                case 2:
                    twoSonsCase(foundedNode);
                    break;
            }
            size--;
            return true;
        }
        return false;
    }
    private void zeroSonsCase(TreeNode foundedBranching,TreeNode foundedNode){
        if (foundedBranching.rightson==foundedNode){
            foundedBranching.rightson=null;
        }else if (foundedBranching.leftSon==foundedNode){
            foundedBranching.leftSon=null;
        }else{
            root=null;
        }
    }
    private void oneSonCase(TreeNode foundedBranching,TreeNode foundedNode){
        if (foundedNode.rightson!=null){
            if (foundedBranching!=foundedNode){
                foundedBranching.rightson=foundedNode.rightson;
            }else{
                root=foundedBranching.rightson;
            }
        }else {
            if (foundedBranching!=foundedNode){
                foundedBranching.leftSon=foundedNode.leftSon;
            }else{
                root=foundedBranching.leftSon;
            }
        }
    }
    private void twoSonsCase(TreeNode root){
        TreeNode smallestBrancing=findBranchingWithSmallestInRightSubTree(root.rightson);
        if (smallestBrancing!=root.rightson){
            root.nodeData=smallestBrancing.leftSon.nodeData;
        }else{
            root.nodeData=root.rightson.nodeData;
        }
        TreeNode chosenSon=smallestBrancing.leftSon;;
        if (smallestBrancing==root.rightson) {
            smallestBrancing=root;
            chosenSon=root.rightson;
        }
        switch (chosenSon.getNumberOfSuns()) {
            case 0:
                zeroSonsCase(smallestBrancing, chosenSon);
                break;
            case 1:
                oneSonCase(smallestBrancing, chosenSon);
                break;
        }
    }

    private TreeNode findBranchingWithSmallestInRightSubTree(TreeNode root){
        TreeNode current=root;
        if (current.leftSon!=null){
            while (current.leftSon.leftSon!=null) {
                current = current.leftSon;
            }
        }
        return current;
    }


    private TreeNode determineSon(TreeNode foundedBranching,int toDetermine){
        if (foundedBranching.rightson!=null){
            if (foundedBranching.rightson.nodeData==toDetermine){
                return foundedBranching.rightson;
            }
        }else if(foundedBranching.leftSon!=null){
            if (foundedBranching.leftSon.nodeData==toDetermine){
                return foundedBranching.leftSon;
            }
        }return null;
    }

    private TreeNode findBranching(int searchVal) {
        TreeNode currentNode = root;
        while (currentNode != null&&currentNode.nodeData!=searchVal) {
            if (currentNode.nodeData > searchVal) {
                if (currentNode.leftSon!=null){
                    if (currentNode.leftSon.nodeData==searchVal){
                        return currentNode;
                    }
                }
                currentNode = currentNode.leftSon;
            } else if (currentNode.nodeData < searchVal) {
                if (currentNode.rightson!=null){
                    if (currentNode.rightson.nodeData==searchVal){
                        return currentNode;
                    }
                }currentNode = currentNode.rightson;
            }
        }
        return currentNode;
    }
    public static void main(String[] args){
        BinaryTree myTree=new BinaryTree(3);
        System.out.println(myTree.root.nodeData==3);
        myTree.add(4);
        System.out.println(myTree.root.rightson.nodeData==4);
        myTree.add(2);
        System.out.println(!myTree.add(2));
        System.out.println(myTree.root.getNumberOfSuns()==2);
        System.out.println(myTree.size()==3);
        System.out.println(myTree.root.depth==0);
        System.out.println(myTree.contains(4)==1);
        myTree.delete(3);
        System.out.println(myTree.size()==2);
        System.out.println(!myTree.delete(3));
        System.out.println(myTree.delete(4));
        myTree.delete(2);
        System.out.println(myTree.size==0);
        System.out.println(myTree.delete(2));


    }
}