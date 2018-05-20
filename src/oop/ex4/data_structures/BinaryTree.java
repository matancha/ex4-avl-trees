package oop.ex4.data_structures;

public class BinaryTree {
    private TreeNode root;

    public BinaryTree(){
        this.root = new TreeNode(5, null, 0);
    }

    public BinaryTree(int rootData){
        this.root=new TreeNode(rootData,null,0);
    }

    protected class TreeNode{
        private TreeNode ancestor;
        private TreeNode leftSon;
        private TreeNode rightSon;
        protected int nodeData;
        protected int depth;
        TreeNode(int nodeData, TreeNode ancestor, int depth) {
            this.depth = depth;
            this.ancestor = ancestor;
            this.nodeData = nodeData;
            this.leftSon = null;
            this.rightSon = null;
        }
    }

    public boolean add(int newValue) {
        return addHelper(newValue, root);
    }

    protected boolean addHelper(int newValue, TreeNode currentNode) {
        if (currentNode.nodeData > newValue) {
            if (currentNode.leftSon != null) {
                return addHelper(newValue, currentNode.leftSon);
            } else {
                currentNode.leftSon = new TreeNode(newValue, currentNode,currentNode.depth+1);
            }
        } else if (currentNode.nodeData < newValue) {
            if (currentNode.rightSon != null) {
                return addHelper(newValue, currentNode.rightSon);
            } else {
                currentNode.rightSon = new TreeNode(newValue, currentNode,currentNode.depth+1);
            }
        } else {
            return false;
        }
        return true;
    }

    public int contains(int searchVal){
        TreeNode currentNode = root;
        while (currentNode != null) {
            if (currentNode.nodeData > searchVal) {
                currentNode = currentNode.leftSon;
            } else if (currentNode.nodeData < searchVal) {
                currentNode = currentNode.rightSon;
            } else {
                return currentNode.depth;
            }
        }
        return -1;
    }

}
