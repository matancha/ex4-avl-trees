package oop.ex4.data_structures;

public class AvlTree extends BinaryTree {
	public AvlTree(int rootData){
		super(5);
	}

	protected TreeNode getRoot(){
		return root;
	}

	private enum UnbalancedState {RL, RR, LL, LR, DEFAULT}

	/**
	 * Add a new node with the given key to the tree.
	 *
	 * @param newValue the value of the new node to add.
	 * @return true if the value to add is not already in the tree and it was successfully added,
	 * false otherwise.
	 */
	@Override
	public boolean add(int newValue){
		if (! super.add(newValue)) {
			return false;
		}

		root = balanceTree(newValue, root);
		return true;
	}

	public boolean delete(int toDelete){
		if (! super.delete(toDelete)){
			return false;
		}

		root = balanceTree(toDelete, root);
		return true;
	}

	private TreeNode balanceTree(int newValue, TreeNode currentNode) {
		if (currentNode == null) {
			return null;
		}

		if (currentNode.nodeData > newValue) {
			currentNode.leftSon = balanceTree(newValue, currentNode.leftSon);
		}
		else if (currentNode.nodeData < newValue) {
			currentNode.rightSon = balanceTree(newValue, currentNode.rightSon);
		}

		currentNode.height = getHeight(currentNode);
		if (Math.abs(getBalanceFactor(currentNode)) == 2) {
			return rotate(currentNode);
		}
		return currentNode;
	}

	private int getBalanceFactor(TreeNode node) {
		return getHeight(node.leftSon) - getHeight(node.rightSon);
	}

	private int getHeight(TreeNode node) {
		if (node == null) {
			return -1;
		}

		return Math.max(getHeight(node.leftSon),
				getHeight(node.rightSon)) + 1;
	}

	private TreeNode rotate(TreeNode unbalancedNode) {
		TreeNode subtreeRoot = unbalancedNode;
		UnbalancedState unbalancedType = getUnbalancedState(unbalancedNode);

		switch (unbalancedType) {
			case LL:
				subtreeRoot = rightRotate(unbalancedNode);
				break;
			case LR:
				unbalancedNode.leftSon = leftRotate(unbalancedNode.leftSon);
				subtreeRoot = rightRotate(unbalancedNode);
				break;
			case RR:
				subtreeRoot = leftRotate(unbalancedNode);
				break;
			case RL:
				unbalancedNode.rightSon = rightRotate(unbalancedNode.rightSon);
				subtreeRoot = leftRotate(unbalancedNode);
				break;
		}
		subtreeRoot.rightSon.height = getHeight(subtreeRoot.rightSon);
		subtreeRoot.leftSon.height = getHeight(subtreeRoot.leftSon);
		subtreeRoot.height = getHeight(subtreeRoot);

		return subtreeRoot;
	}

	private TreeNode leftRotate(TreeNode node) {
		TreeNode subtreeRoot;

		subtreeRoot = node.rightSon;
		subtreeRoot.leftSon = node;
		node.rightSon = null;
		return subtreeRoot;
	}

	private TreeNode rightRotate(TreeNode node) {
		TreeNode subtreeRoot;

		subtreeRoot = node.leftSon;
		subtreeRoot.rightSon = node;
		node.leftSon = null;
		return subtreeRoot;
	}

	private UnbalancedState getUnbalancedState(TreeNode unbalancedNode) {
		if (getBalanceFactor(unbalancedNode) == 2) {
			if (unbalancedNode.leftSon.leftSon != null){
				return UnbalancedState.LL;
			} else if (unbalancedNode.leftSon.rightSon != null) {
				return UnbalancedState.LR;
			}
		} else if (getBalanceFactor(unbalancedNode) == -2) {
			if (unbalancedNode.rightSon.leftSon != null){
				return UnbalancedState.RL;
			} else if (unbalancedNode.rightSon.rightSon != null) {
				return UnbalancedState.RR;
			}
		}
		return UnbalancedState.DEFAULT;
	}

	public static int findMinNodes(int h) {
		double res = fib(h+3)-1;
		return (int)res;
	}

	private static double fib(int n) {
		double phi = (Math.sqrt(5)+1)/2;
		return (Math.pow(phi, n)-Math.pow(-phi, -n))/Math.sqrt(5);
	}

	public static int findMaxNodes(int h) {
		return (int)Math.pow(2, h+1)-1;
	}
}
