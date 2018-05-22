package oop.ex4.data_structures;

public class AvlTree extends BinaryTree {
	public AvlTree(int rootData){
		super(5);
	}

	protected TreeNode getRoot(){
		return root;
	}

	private enum ViolationTypes {RL, RR, LL, LR}

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

	@Override
	public boolean delete(int toDelete){
		if (! super.delete(toDelete)){
			return false;
		}

		root = balanceTree(toDelete, root);
		return true;
	}

	/**
	 * This function rebalances the tree if the AVL invariant is violated
	 * @param addedValue the value that was added to the tree
	 * @param currentNode node - initialize as root
	 * @return root of the tree after balancing
	 */
	private TreeNode balanceTree(int addedValue, TreeNode currentNode) {
		if (currentNode == null) {
			return null;
		}

		if (currentNode.nodeData > addedValue) {
			currentNode.leftSon = balanceTree(addedValue, currentNode.leftSon);
		}
		else if (currentNode.nodeData < addedValue) {
			currentNode.rightSon = balanceTree(addedValue, currentNode.rightSon);
		}

		/* Maintain height attribute of node */
		currentNode.height = getHeight(currentNode);
		if (Math.abs(getBalanceFactor(currentNode)) == 2) {
			return rotate(currentNode);
		}
		return currentNode;
	}

	/**
	 * Gets the current balance factor of the node
	 * @param node node checked
	 * @return balance factor - int between -2 and 2. Where 2 is left-heavy, and -2 right-heavy.
	 */
	private int getBalanceFactor(TreeNode node) {
		return getHeight(node.leftSon) - getHeight(node.rightSon);
	}

	/**
	 * Gets the height of a node in the tree
	 * @param node node checked
	 * @return height of node
	 */
	private int getHeight(TreeNode node) {
		if (node == null) {
			return -1;
		}

		return Math.max(getHeight(node.leftSon),
				getHeight(node.rightSon)) + 1;
	}

	/**
	 * Rotates the subtree that the node is the root of, according to the type of violation
	 * @param unbalancedNode node that violates the AVL invariant
	 * @return root of subtree (can be different than the one supplied)
	 */
	private TreeNode rotate(TreeNode unbalancedNode) {
		TreeNode subtreeRoot = unbalancedNode;

		try {
			ViolationTypes violationType = getViolationType(unbalancedNode);
			switch (violationType) {
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
		
		catch (NoViolationException e) { return subtreeRoot; }
	}

	/**
	 * Rotates the tree left
	 * @param subtreeRoot root of subtree to rotate
	 * @return root of subtree (can be different than the one supplied)
	 */
	private TreeNode leftRotate(TreeNode subtreeRoot) {
		TreeNode newRoot = subtreeRoot.rightSon;
		TreeNode transferredSon = newRoot.leftSon;

		newRoot.leftSon = subtreeRoot;
		subtreeRoot.rightSon = transferredSon;

		return newRoot;
	}

	/**
	 * Rotates the tree right
	 * @param subtreeRoot root of subtree to rotate
	 * @return root of subtree (can be different than the one supplied)
	 */
	private TreeNode rightRotate(TreeNode subtreeRoot) {
		TreeNode newRoot = subtreeRoot.leftSon;
		TreeNode transferredSon = newRoot.rightSon;

		newRoot.rightSon = subtreeRoot;
		subtreeRoot.leftSon = transferredSon;

		return newRoot;
	}

	/**
	 * Determines what kind of violation has occurred in the node violating the AVL invariant
	 * @param unbalancedNode node violating AVL invariant
	 * @return violation type
	 */
	private ViolationTypes getViolationType(TreeNode unbalancedNode) throws NoViolationException {
		if (getBalanceFactor(unbalancedNode) == 2) {
			if (unbalancedNode.leftSon.leftSon != null){
				return ViolationTypes.LL;
			} else if (unbalancedNode.leftSon.rightSon != null) {
				return ViolationTypes.LR;
			}
		} else if (getBalanceFactor(unbalancedNode) == -2) {
			if (unbalancedNode.rightSon.leftSon != null){
				return ViolationTypes.RL;
			} else if (unbalancedNode.rightSon.rightSon != null) {
				return ViolationTypes.RR;
			}
		}
		throw new NoViolationException();
	}

	/**
	 * Get minimum number of nodes in a tree of height h
	 * @param h height of tree
	 * @return minimum
	 */
	public static int findMinNodes(int h) {
		double res = fib(h+3)-1;
		return (int)res;
	}

	/**
	 * Calculates the nth Fibonacci number, using Binet's Formula
	 * see also: http://www.maths.surrey.ac.uk/hosted-sites/R.Knott/Fibonacci/fibFormula.html
	 * @param n Fibonacci number wanted
	 * @return fibonacci number
	 */
	private static double fib(int n) {
		double phi = (Math.sqrt(5)+1)/2;
		return (Math.pow(phi, n)-Math.pow(-phi, -n))/Math.sqrt(5);
	}

	/**
	 * Get maximum number of nodes in a tree of height h
	 * @param h height of tree
	 * @return maximum
	 */
	public static int findMaxNodes(int h) {
		return (int)Math.pow(2, h+1)-1;
	}
}
