package oop.ex4.data_structures;

import java.util.Iterator;


public class BinaryTree implements Iterable{
    /* root of the tree. */
    protected TreeNode root;
    /* current size of the tree. */
    protected int size;

    /*----=Constructors=----*/

    /**
     * constructor that creates tree without nodes.
     */
    public BinaryTree() {
        root = null;
    }

    /**
     * Constructor that creates a tree from a given array.
     * @param data int array
     */
    public BinaryTree(int[] data) {
        for (int nodeData : data) {
            add(nodeData);
        }
    }

    /**
     * constructor that creates tree with with the only one node.
     * @param rootData-data for the first node.
     */
    public BinaryTree(int rootData){
        this.root=new TreeNode(rootData,0);
        this.size=1;
    }

    /**
     * this class implements node of Binary Tree.
     */
    protected class TreeNode{
        /*data of the node. */
        protected int nodeData;
        /*left sone of the node. */
        protected TreeNode leftSon;
        /*right sone of the node. */
        protected TreeNode rightSon;
        /*depth of the node, according to the root. */
        protected int depth;

        protected int height;
        /*----=Constructor=----*/

        /**
         * This constructor creates node with inserted data.
         * @param nodeData-data that contained by node
         * @param depth- depth of the node according to the root.
         */
        TreeNode(int nodeData,int depth){
            this.depth=depth;
            this.nodeData=nodeData;
            this.leftSon=null;
            this.rightSon =null;
            this.height = 0;
        }

        public int getHeight() {
            return height;
        }

        /**
         * this function for counting number of suns for deleting.
         * @return number of the sons that are not null
         */
        public int getNumberOfSuns(){
            int counter=0;
            if (rightSon !=null){
                counter++;
            }if (leftSon!=null){
                counter++;
            }
            return counter;
        }

    }
    public Iterator<Integer> iterator(){
        class TreeIterator implements Iterator<Integer> {
            private TreeNode current;
            TreeIterator(){
                current = root;
            } public boolean hasNext(){
                if (current.leftSon!=null){
                    return true;
                }
                return false;
            } public Integer next(){
                if (current.leftSon!=null){
                    current=current.leftSon;
                    return next();
                } if (current.rightSon !=null){
                    current=current=current.rightSon;
                    return next();
                } return current.nodeData;
            }
        }
        return new TreeIterator();
    }

    /**
     * Add a new node with given key to the tree.
     * @param newValue the value of the new node to add
     * @return true if the value to add not already in the tree
     * and it was successfully added,false otherwise.
     */
    public boolean add(int newValue){
        if (root==null){
            root=new TreeNode(newValue,0);
            return true;
        }
        if (contains(newValue)==-1){
            size++;
            return addHelper(newValue,root);
        }
        return false;
    }

    /**
     * help to add given value with recursion.
     * @param newValue the value of the new node to add
     * @param currentNode with data of this node newValue is compared
     * @return true if the value to add not already in the tree
     * and it was successfully added,false otherwise.
     */
    private boolean addHelper(int newValue,TreeNode currentNode){
        if (currentNode.nodeData>newValue){
            if (currentNode.leftSon!=null){
                return addHelper(newValue,currentNode.leftSon);
            }else{
                currentNode.leftSon=new TreeNode(newValue,currentNode.depth+1);
            }
        }else if(currentNode.nodeData<newValue){
            if (currentNode.rightSon !=null){
                return addHelper(newValue,currentNode.rightSon);
            }else{
                currentNode.rightSon =new TreeNode(newValue,currentNode.depth+1);
            }
        }
        return true;
    }

    /**
     * check whether the tree contains the given input value.
     * @param searchVal the value to search for.
     * @return the depth of the node (0 for the root) with the given value
     * if it was found in the tree,-1 otherwise.
     */
    public int contains(int searchVal){
        if (searchVal==root.nodeData){
            return 0;
        }
        TreeNode foundedBranching=findBranching(searchVal);
        if (foundedBranching!=null){
            return foundedBranching.depth+1;
        }
        return -1;
    }

    /**
     * @return the number of nodes in the tree.
     */
    public int size(){
        return size;
    }

    /**
     * Removes the node with the given value from tree,if it exists.
     * @param toDelete The value to remove from the tree.
     * @return true if the given value was found and deleted,false otherwise.
     */
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

    /**
     * Delete inserted node if the node have no sons.
     * If root have to be deleted,will make tree without nodes.
     * @param foundedBranching Node that one of the sons is node for deleting
     * @param foundedNode Node for deleting
     */
    private void zeroSonsCase(TreeNode foundedBranching,TreeNode foundedNode){
        if (foundedBranching.rightSon ==foundedNode){
            foundedBranching.rightSon =null;
        }else if (foundedBranching.leftSon==foundedNode){
            foundedBranching.leftSon=null;
        }else{
            root=null;
        }
    }

    /**
     * Delete inserted node if the node have one of the sons,right of left.
     * @param foundedBranching Node that one of the sons is node for deleting
     * @param foundedNode Node for deleting
     */
    private void oneSonCase(TreeNode foundedBranching,TreeNode foundedNode){
        if (foundedNode.rightSon !=null){
            if (foundedBranching!=foundedNode){
                foundedBranching.rightSon =foundedNode.rightSon;
                foundedBranching.rightSon.depth--;
            }else{
                root=foundedBranching.rightSon;
                root.depth--;
            }
        }else {
            if (foundedBranching!=foundedNode){
                foundedBranching.leftSon=foundedNode.leftSon;
                foundedBranching.leftSon.depth--;
            }else{
                root=foundedBranching.leftSon;
                root.depth--;
            }
        }
    }

    /**
     * Delete the node when it does have 2 sons.
     * @param root node that have to be deleted.
     */
    private void twoSonsCase(TreeNode root){
        TreeNode smallestBrancing= successor(root.rightSon);
        if (smallestBrancing!=root.rightSon){
            root.nodeData=smallestBrancing.leftSon.nodeData;
        }else{
            root.nodeData=root.rightSon.nodeData;
        }
        TreeNode chosenSon=smallestBrancing.leftSon;;
        if (smallestBrancing==root.rightSon) {
            smallestBrancing=root;
            chosenSon=root.rightSon;
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

    /**
     * this method find branching with smallest node in subtree with inserted root.
     * @param root-root of the subtree where we are searching for the smallest node.
     * @return Node,such that left son if it is not null it is smallest node in subtree.
     */
    private TreeNode successor(TreeNode root){
        TreeNode current=root;
        if (current.leftSon!=null){
            while (current.leftSon.leftSon!=null) {
                current = current.leftSon;
            }
        }
        return current;
    }

    /**
     * This method is determine which of the sons of founded branching
     * have inserted value.
     * @param foundedBranching Node such that one pf the sons have value that searched for.
     * @param toDetermine founded value.
     * @return Rightson if right son of the branching have inserted value,left if leftson have inserted
     * value,null otherwise.
     */
    private TreeNode determineSon(TreeNode foundedBranching,int toDetermine){
        if (foundedBranching.rightSon !=null){
            if (foundedBranching.rightSon.nodeData==toDetermine){
                return foundedBranching.rightSon;
            }
        }else if(foundedBranching.leftSon!=null){
            if (foundedBranching.leftSon.nodeData==toDetermine){
                return foundedBranching.leftSon;
            }
        }return null;
    }

    /**
     * find node,such that one of the sons is searched Value,if search Value in the tree,null otherwise.
     * For example if w ehave tree 2,3,4 and 3 is a root and value that we are
     * searching for it is 4,so node with data 3 will be returned,because one of the sons
     * of 3 is node with data 4.
     * @param searchVal value that we are search in the tree.
     * @return Node such that one of his sons is node with searched value,null otherwise.
     */
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
                if (currentNode.rightSon !=null){
                    if (currentNode.rightSon.nodeData==searchVal){
                        return currentNode;
                    }
                }currentNode = currentNode.rightSon;
            }
        }
        return currentNode;
    }
    public static void main(String[] args){
        BinaryTree myTree=new BinaryTree(3);
        System.out.println(myTree.root.nodeData==3);
        myTree.add(4);
        System.out.println(myTree.contains(4)==1);
        System.out.println(myTree.root.rightSon.nodeData==4);
        myTree.add(2);
        System.out.println(myTree.contains(2)==1);
        System.out.println(!myTree.add(2));
        System.out.println(myTree.root.getNumberOfSuns()==2);
        System.out.println(myTree.size()==3);
        System.out.println(myTree.contains(4)==1);
        myTree.delete(3);
        System.out.println(myTree.contains(3)==-1);
        System.out.println(myTree.size()==2);
        System.out.println(!myTree.delete(3));
        System.out.println(myTree.root.depth==0);
        System.out.println(myTree.contains(4)==0);
        System.out.println(myTree.delete(4));
        System.out.println(myTree.contains(2)==0);

        myTree.delete(2);
        System.out.println(myTree.size==0);
        System.out.println(!myTree.delete(2));
//        for (Object node:myTree){
//            System.out.println(node);
//        }

    }
}