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

	private TreeNode balanceTree(int newValue, TreeNode currentNode) {
		if (currentNode == null) {
			return null;
		}

		if (currentNode.nodeData > newValue) {
			currentNode.leftSon = balanceTree(newValue, currentNode.leftSon);
		}
		else if (currentNode.nodeData < newValue) {
			currentNode.rightson = balanceTree(newValue, currentNode.rightson);
		}

		currentNode.height = getHeight(currentNode);
		if (Math.abs(getBalanceFactor(currentNode)) == 2) {
			return rotate(currentNode);
		}
		return currentNode;
	}

	/*public boolean delete(int toDelete){
		if (contains(toDelete) == -1){
			return false;
		}

		root = deleteHelper(toDelete, (TreeNode)root);
		return true;
	}

	private TreeNode deleteHelper(int toDelete, TreeNode currentNode) {
		if (currentNode == null) {
			return null;
		} else if (currentNode.nodeData == toDelete){
			return currentNode.rightSon;
		}

		if (currentNode.nodeData > toDelete) {
			currentNode.leftSon = deleteHelper(toDelete, currentNode.leftSon);
		}
		else if (currentNode.nodeData < toDelete) {
			currentNode.rightSon = deleteHelper(toDelete, currentNode.rightSon);
		}

		return currentNode;
	}*/

	private int getBalanceFactor(TreeNode node) {
		return getHeight(node.leftSon) - getHeight(node.rightson);
	}

	private int getHeight(TreeNode node) {
		if (node == null) {
			return -1;
		}

		return Math.max(getHeight(node.leftSon),
				getHeight(node.rightson)) + 1;
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
				unbalancedNode.rightson = rightRotate(unbalancedNode.rightson);
				subtreeRoot = leftRotate(unbalancedNode);
				break;
		}
		subtreeRoot.rightson.height = getHeight(subtreeRoot.rightson);
		subtreeRoot.leftSon.height = getHeight(subtreeRoot.leftSon);
		subtreeRoot.height = getHeight(subtreeRoot);

		return subtreeRoot;
	}

	private TreeNode leftRotate(TreeNode node) {
		TreeNode subtreeRoot;

		subtreeRoot = node.rightson;
		subtreeRoot.leftSon = node;
		node.rightson = null;
		return subtreeRoot;
	}

	private TreeNode rightRotate(TreeNode node) {
		TreeNode subtreeRoot;

		subtreeRoot = node.leftSon;
		subtreeRoot.rightson = node;
		node.leftSon = null;
		return subtreeRoot;
	}

	private UnbalancedState getUnbalancedState(TreeNode unbalancedNode) {
		if (getBalanceFactor(unbalancedNode) == 2) {
			if (unbalancedNode.leftSon.leftSon != null){
				return UnbalancedState.LL;
			} else if (unbalancedNode.leftSon.rightson != null) {
				return UnbalancedState.LR;
			}
		} else if (getBalanceFactor(unbalancedNode) == -2) {
			if (unbalancedNode.rightson.leftSon != null){
				return UnbalancedState.RL;
			} else if (unbalancedNode.rightson.rightson != null) {
				return UnbalancedState.RR;
			}
		}
		return UnbalancedState.DEFAULT;
	}
}
