package oop.ex4.data_structures;

public class AvlTree extends BinaryTree {
	private AvlNode root;

	public AvlTree(){
		this.root = new AvlNode(5, null, 0);
	}

	protected AvlNode getRoot(){
		return root;
	}

	public class AvlNode extends TreeNode {
		private int height;
		protected AvlNode leftSon;
		protected AvlNode rightSon;

		private AvlNode(int nodeData, AvlNode ancestor, int depth){
			super(nodeData, ancestor, depth);
			this.height = 0;
			this.leftSon = null;
			this.rightSon = null;
		}

		public int getHeight() {
			return height;
		}
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
		if (contains(newValue) != -1){
			return false;
		}

		root = addHelper(newValue, root);
		return true;
	}

	protected AvlNode addHelper(int newValue, AvlNode currentNode) {
		if (currentNode == null) {
			return new AvlNode(newValue, null, 0);
		}

		if (currentNode.nodeData > newValue) {
			currentNode.leftSon = addHelper(newValue, currentNode.leftSon);
		}
		else if (currentNode.nodeData < newValue) {
			currentNode.rightSon = addHelper(newValue, currentNode.rightSon);
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

		root = deleteHelper(toDelete, root);
		return true;
	}

	private AvlNode deleteHelper(int toDelete, AvlNode currentNode) {
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
	}

	public int contains(int searchVal) {
		AvlNode currentNode = root;
		int depth = 0;
		while (currentNode != null) {
			if (currentNode.nodeData > searchVal) {
				currentNode = currentNode.leftSon;
			} else if (currentNode.nodeData < searchVal) {
				currentNode = currentNode.rightSon;
			} else {
				return depth;
			}
			depth += 1;
		}
		return -1;
	}*/

	private int getHeight(AvlNode node) {
		if (node == null) {
			return -1;
		}

		return Math.max(getHeight(node.leftSon),
				getHeight(node.rightSon)) + 1;
	}

	private int getBalanceFactor(AvlNode node) {
		return getHeight(node.leftSon) - getHeight(node.rightSon);
	}

	private AvlNode rotate(AvlNode unbalancedNode) {
		AvlNode subtreeRoot = unbalancedNode;
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

	private AvlNode leftRotate(AvlNode node) {
		AvlNode subtreeRoot;

		subtreeRoot = node.rightSon;
		subtreeRoot.leftSon = node;
		node.rightSon = null;
		return subtreeRoot;
	}

	private AvlNode rightRotate(AvlNode node) {
		AvlNode subtreeRoot;

		subtreeRoot = node.leftSon;
		subtreeRoot.rightSon = node;
		node.leftSon = null;
		return subtreeRoot;
	}

	private UnbalancedState getUnbalancedState(AvlNode unbalancedNode) {
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
}
