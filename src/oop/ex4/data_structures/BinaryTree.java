package oop.ex4.data_structures;

import java.util.Iterator;


public class BinaryTree implements Iterable<Integer> {
    /* root of the tree. */
    protected TreeNode root;
    /* current size of the tree. */
    protected int size;

    /*----=Constructors=----*/
    /**
     * constructor that creates tree without nodes.
     */
    BinaryTree(){
        root=null;
        size=0;
    }

    /**
     * Constructor that creates a tree from a given array.
     * @param data int array
     */
    public BinaryTree(int[] data) {
        try {
            if (data == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            return;
        }

        for (int nodeData : data) {
            add(nodeData);
        }
    }


    /**
     * This constructor create copy of
     * @param binaryTree Tree for coping
     */
    public BinaryTree(BinaryTree binaryTree){
         root=null;
         Iterator<Integer> iter=binaryTree.iterator();
         if (iter == null){
             return;
         }
         int[] data=new int[binaryTree.size()];
         for (int i=0;i<binaryTree.size();i++){
             data[i]=iter.next();
         }
         inserter(0,binaryTree.size()-1,data);
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
        /* height of the node */
        protected int height;

        /*----=Constructors=----*/
        /**
         * This constructor creates node with inserted data.
         * @param nodeData-data that contained by node
         */
        TreeNode(int nodeData){
            this.nodeData=nodeData;
            this.leftSon=null;
            this.rightSon=null;
            this.height = 0;
        }

        /**
         * this function for counting number of suns for deleting.
         * @return number of the sons that are not null
         */
        public int getNumberOfSons(){
            int counter=0;
            if (rightSon!=null){
                counter++;
            }if (leftSon!=null){
                counter++;
            }
            return counter;
        }

    }

    /**
     * this method for iteration on tree data.
     * It had to create array that contains sorted data,because our design
     * without ancestor of node and we are not able to use successor for iteration.
     * @return Iterator of tree data.
     */
    public Iterator<Integer> iterator(){
        class TreeIterator implements Iterator<Integer>{
            /*this array for containing all data of tree in sorted order. */
            private int[] sortedList;
            /*index in inserting tree data to the array and for iteration.*/
            int index;
            /*Creates TreeIterator. */
            TreeIterator(){
                sortedList=new int[size];
                index=0;
                createList(root);
                index=0;
            }

            /**
             * this function check if there is anymore elements.
             * @return true if there are elements that was not returned,false otherwise.
             */
            public boolean hasNext(){
                return index<sortedList.length;
            }

            /**
             * this check if there are elements that weren't returned,and return next if there is.
             * @return next value if there is some.
             */
            @Override
            public Integer next() {
                if (hasNext()){
                    index++;
                    return sortedList[index-1];
                }
                return -1;
            }

            /**
             * this method create sorted array.
             * It firstly will come down to
             * @param node-node of the tree for next adding
             */
            private void createList(TreeNode node){
                if (node == null){
                    sortedList = new int[0];
                    return;
                }

                if (node.leftSon!=null){
                    createList(node.leftSon);
                }sortedList[index]=node.nodeData;
                index++;
                if (node.rightSon!=null) {
                    createList(node.rightSon);
                }
            }
        }
        return new TreeIterator();
    }

    /**
     * This method insert sorted array into the tree.
     * @param low low bound of part of the array
     *            that we are working with
     * @param high high bound
     * @param data integer for inserting into the tree.
     */
    private void inserter(int low,int high,int[] data){
        if (high>=low){
            int mid=(low+high)/2;
            add(data[mid]);
            inserter(low,mid-1,data);
            inserter(mid+1,high,data);
        }

    }

    /**
     * Add a new node with given key to the tree.
     * @param newValue the value of the new node to add
     * @return true if the value to add not already in the tree
     * and it was successfully added,false otherwise.
     */
    public boolean add(int newValue){
        if (root==null){
            root=new TreeNode(newValue);
            size++;
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
                currentNode.leftSon=new TreeNode(newValue);
            }
        }else if(currentNode.nodeData<newValue){
            if (currentNode.rightSon!=null){
                return addHelper(newValue,currentNode.rightSon);
            }else{
                currentNode.rightSon=new TreeNode(newValue);
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
        TreeNode currentNode = root;
        int i = 0;
        while (currentNode != null) {
            if (currentNode.nodeData > searchVal) {
                currentNode = currentNode.leftSon;
                } else if (currentNode.nodeData < searchVal) {
                currentNode = currentNode.rightSon;
                } else {
                return i;
                }
            i += 1;
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
            switch (foundedNode.getNumberOfSons()){
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
        if (foundedBranching.rightSon==foundedNode){
            foundedBranching.rightSon=null;
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
        if (foundedNode.rightSon!=null){
            if (foundedBranching!=foundedNode){
                if (foundedBranching.rightSon == foundedNode){
                    foundedBranching.rightSon=foundedNode.rightSon;
                } else if (foundedBranching.leftSon == foundedNode){
                    foundedBranching.leftSon=foundedNode.rightSon; }
            }else{
                root=foundedBranching.rightSon;
            }
        }else {
            if (foundedBranching!=foundedNode){
                if (foundedBranching.rightSon == foundedNode){
                    foundedBranching.rightSon=foundedNode.leftSon;
                } else if (foundedBranching.leftSon == foundedNode){
                    foundedBranching.leftSon=foundedNode.leftSon;
                }
            }else{
                root=foundedBranching.leftSon;
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
        switch (chosenSon.getNumberOfSons()) {
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
     * @return Node,such that left son if it is not null it is mallest node in subtree.
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
     * @return rightSon if right son of the branching have inserted value,left if leftson have inserted
     * value,null otherwise.
     */
    private TreeNode determineSon(TreeNode foundedBranching,int toDetermine){
        if (foundedBranching.rightSon!=null){
            if (foundedBranching.rightSon.nodeData==toDetermine){
                return foundedBranching.rightSon;
            }
        } if (foundedBranching.leftSon!=null){
            if (foundedBranching.leftSon.nodeData==toDetermine){
                return foundedBranching.leftSon;
            }
        }return null;
    }

    /**
     * find node,such that one of the sons is searched Value,if search Value in the tree,null otherwise.
     * For example if we have tree 2,3,4 and 3 is a root and value that we are
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
                if (currentNode.rightSon!=null){
                    if (currentNode.rightSon.nodeData==searchVal){
                        return currentNode;
                    }
                }currentNode = currentNode.rightSon;
            }
        }
        return currentNode;
    }
}