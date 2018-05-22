package oop.ex4.data_structures;

import org.junit.*;

public class testAvlTree {
	private AvlTree tree;

	public testAvlTree(){
		tree = new AvlTree(5);
	}

	@Test
	public void addNodeToTree() {
		Assert.assertEquals(true, tree.add(3));
		Assert.assertEquals(1, tree.getRoot().getHeight());
		Assert.assertEquals(3, tree.getRoot().leftSon.nodeData);
	}

	@Test
	public void rotationChangesRoot() {
		/* LL violation */
		tree.add(3);
		tree.add(2);
		Assert.assertEquals(3, tree.getRoot().nodeData);
		Assert.assertEquals(5, tree.getRoot().rightSon.nodeData);
		Assert.assertEquals(2, tree.getRoot().leftSon.nodeData);
		Assert.assertEquals(1, tree.getRoot().getHeight());
	}

	@Test
	public void regularRotation() {
		/* LR violation */
		tree.add(6);
		tree.add(4);
		tree.add(2);
		tree.add(3);
		Assert.assertEquals(5, tree.getRoot().nodeData);
		Assert.assertEquals(3, tree.getRoot().leftSon.nodeData);
		Assert.assertEquals(4, tree.getRoot().leftSon.rightSon.nodeData);
		Assert.assertEquals(2, tree.getRoot().leftSon.leftSon.nodeData);
	}

	@Test
	public void rightRightViolation() {
		tree.add(6);
		tree.add(8);
		Assert.assertEquals(6, tree.getRoot().nodeData);
		Assert.assertEquals(8, tree.getRoot().rightSon.nodeData);
		Assert.assertEquals(5, tree.getRoot().leftSon.nodeData);
	}

	@Test
	public void rightLeftViolation() {
		tree.add(8);
		tree.add(6);
		Assert.assertEquals(6, tree.getRoot().nodeData);
		Assert.assertEquals(8, tree.getRoot().rightSon.nodeData);
		Assert.assertEquals(5, tree.getRoot().leftSon.nodeData);
	}

	@Test
	public void deleteNodeWithNoSons() {
		tree.add(8);
		Assert.assertEquals(true, tree.delete(8));
	}

	@Test
	public void deleteNodeWithOneSon() {
		tree.add(4);
		tree.add(10);
		tree.add(11);
		Assert.assertEquals(true, tree.delete(10));
		Assert.assertEquals(11, tree.getRoot().rightSon.nodeData);
	}

	@Test
	public void getMinNodes() {
		Assert.assertEquals(2, AvlTree.findMinNodes(1));
		Assert.assertEquals(4, AvlTree.findMinNodes(2));
		Assert.assertEquals(7, AvlTree.findMinNodes(3));
		Assert.assertEquals(12, AvlTree.findMinNodes(4));
	}

	@Test
	public void getMaxNodes() {
		Assert.assertEquals(3, AvlTree.findMaxNodes(1));
		Assert.assertEquals(7, AvlTree.findMaxNodes(2));
		Assert.assertEquals(15, AvlTree.findMaxNodes(3));
		Assert.assertEquals(31, AvlTree.findMaxNodes(4));
	}
}
