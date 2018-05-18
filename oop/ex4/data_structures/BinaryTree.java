public class BinaryTree {
    protected TreeNode root;
    protected int size;
    BinaryTree(int rootData){
        this.root=new TreeNode(rootData,null,0);
        this.size=1;
    }
    protected class TreeNode{
        protected TreeNode ancestor;
        protected int nodeData;
        protected TreeNode leftSon;
        protected TreeNode rightson;
        protected int depth;
        TreeNode(int nodeData,TreeNode ancestor,int depth){
            this.depth=depth;
            this.ancestor=ancestor;
            this.nodeData=nodeData;
            this.leftSon=null;
            this.rightson=null;
        }
    }
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
                currentNode.leftSon=new TreeNode(newValue,currentNode,currentNode.depth+1);
            }
        }else if(currentNode.nodeData<newValue){
            if (currentNode.rightson!=null){
                return addHelper(newValue,currentNode.rightson);
            }else{
                currentNode.rightson=new TreeNode(newValue,currentNode,currentNode.depth+1);
            }
        }
        return true;
    }
    public int contains(int searchVal){
        TreeNode currentNode=root;
        while (currentNode!=null){
            if (currentNode.nodeData>searchVal){
                currentNode=currentNode.leftSon;
            }else if(currentNode.nodeData<searchVal){
                currentNode=currentNode.rightson;
            }else{
                return currentNode.depth;
            }
        }
        return -1;
    }

}
