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
		return addHelper(newValue, root);
	}

	protected boolean addHelper(int newValue, AvlNode currentNode) {
		boolean isAdded;

		if (currentNode.nodeData > newValue) {
			if (currentNode.leftSon != null) {
				isAdded = addHelper(newValue, currentNode.leftSon);

				if (isAdded) {
					currentNode.height = getHeight(currentNode);
					if (getBalanceFactor(currentNode) == 2) {
						if (currentNode == root) {
							root = rotate(currentNode);
						}
					}
				}
				return isAdded;
			} else {
				currentNode.leftSon = new AvlNode(newValue, currentNode,currentNode.depth+1);
				currentNode.height = getHeight(currentNode);
			}
		} else if (currentNode.nodeData < newValue) {
			if (currentNode.rightSon != null) {
				isAdded = addHelper(newValue, currentNode.rightSon);

				if (isAdded) {
					currentNode.height = getHeight(currentNode);
					if (getBalanceFactor(currentNode) == -2) {
						if (currentNode == root) {
							root = rotate(currentNode);
						}
					}
				}
				return isAdded;
			} else {
				currentNode.rightSon = new AvlNode(newValue, currentNode,currentNode.depth+1);
				currentNode.height = getHeight(currentNode);
			}
		} else {
			return false;
		}

		return true;
	}

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
		AvlNode root = unbalancedNode;
		UnbalancedState unbalancedType = getUnbalancedState(unbalancedNode);

		switch (unbalancedType) {
			case LL:
				root = unbalancedNode.leftSon;
				root.rightSon = unbalancedNode;
				unbalancedNode.leftSon = null;
				root.rightSon.height = getHeight(root.rightSon);
				root.leftSon.height = getHeight(root.leftSon);
				root.height = getHeight(root);
				break;
		}
		return root;
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
