package oop.ex4.data_structures;

import org.junit.*;

public class testAvlTree {
	private AvlTree tree;

	public testAvlTree(){
		tree = new AvlTree();
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
		Assert.assertEquals(true, tree.add(3));
		Assert.assertEquals(true, tree.add(2));
		Assert.assertEquals(3, tree.getRoot().nodeData);
		Assert.assertEquals(5, tree.getRoot().rightSon.nodeData);
		Assert.assertEquals(2, tree.getRoot().leftSon.nodeData);
		Assert.assertEquals(1, tree.getRoot().getHeight());
	}

	@Test
	public void regularRotation() {
		/* LR violation */
		Assert.assertEquals(true, tree.add(6));
		Assert.assertEquals(true, tree.add(4));
		Assert.assertEquals(true, tree.add(2));
		Assert.assertEquals(true, tree.add(3));
		Assert.assertEquals(5, tree.getRoot().nodeData);
		Assert.assertEquals(3, tree.getRoot().leftSon.nodeData);
		Assert.assertEquals(4, tree.getRoot().leftSon.rightSon.nodeData);
		Assert.assertEquals(2, tree.getRoot().leftSon.leftSon.nodeData);
	}
}
