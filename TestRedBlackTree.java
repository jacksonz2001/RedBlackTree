// --== CS400 File Header Information ==--
// Name: <Jackson Zhao>
// Email: <jzhao373@wisc.edu>
// Team: <CA>
// TA: <Abhinav>
// Lecturer: <Florian Heimerl>
// Notes to Grader: <N/A>

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRedBlackTree {

  protected static class Node<T> {
    public T data;
    public RedBlackTree.Node<T> parent; // null for root node
    public RedBlackTree.Node<T> leftChild;
    public RedBlackTree.Node<T> rightChild;
    public boolean isBlack = false;

    public Node(T data) {
      this.data = data;
    }
  }

  /**
   * This tests the case where there is a double red node violation (ie after inserting 5) After 5
   * is inserted, the tree becomes unbalanced, and needs to be fixed by one left rotation and the
   * recoloring of the root 4.
   * 
   * Tests the case where child is red, parent is red, grandparent is black, sibling is null
   */
  @Test
  public void test1() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(3);
    tree.insert(4);

    RedBlackTree.Node<Integer> node = tree.root.rightChild;

    tree.insert(5);

    // Checks the root node and the tree size
    if (!tree.root.equals(node)) {
      fail("Error: Root is supposed to be the Node 4!");
    } else if (tree.size != 3) {
      fail("Error: Tree size was adjusted");
    }

    // Checks the colors of the Nodes
    if (!tree.root.isBlack) {
      fail("Error: Root is supposed to be colored Black");
    } else if (tree.root.rightChild.isBlack) {
      fail("Error: Node 5 should be colored Red");
    } else if (tree.root.leftChild.isBlack) {
      fail("Error: Node 3 should be colored Red");
    }
  }

  /**
   * This tests where there is a double red node in the left subtree of the RBT. After inserting 3,
   * the tree becomes unbalanced, recoloring is the only necessary step
   * 
   * Tests the case where child is red, parent is red, grandparent is black, sibling is red
   */
  @Test
  public void test2() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(10);
    tree.insert(5);
    tree.insert(11);
    tree.insert(4);
    tree.insert(6);

    RedBlackTree.Node<Integer> node = tree.root;

    tree.insert(3);

    // Checks the root node, and the tree size
    if (!tree.root.equals(node)) {
      fail("Error: Root was altered!");
    } else if (tree.size != 6) {
      fail("Error: Tree size was adjusted");
    }

    // Checks the colors of the Nodes
    if (!tree.root.isBlack) {
      fail("Error: Root is supposed to be colored Black");
    } else if (tree.root.leftChild.isBlack) {
      fail("Error: Node 5 should be Red");
    } else if (!tree.root.leftChild.leftChild.isBlack) {
      fail("Error: Node 4 should be Black");
    } else if (!tree.root.leftChild.rightChild.isBlack) {
      fail("Error: Node 6 should be Black");
    } else if (tree.root.leftChild.leftChild.leftChild.isBlack) {
      fail("Error: Node 3 should be Red");
    }
  }

  /**
   * This tests the functionality of the enforceRBTreePropertiesAfterInsert on a triangle case where
   * two rotations are needed, then a recoloring
   * 
   * Tests a case where child is red, parents is red and right child of grandparent, grandparent is
   * black, sibling is null
   */
  @Test
  public void test3() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(14);
    tree.insert(7);
    tree.insert(18);
    tree.insert(1);
    tree.insert(11);
    tree.insert(23);

    RedBlackTree.Node<Integer> node = tree.root;
    RedBlackTree.Node<Integer> node18 = tree.root.rightChild;
    RedBlackTree.Node<Integer> node23 = tree.root.rightChild.rightChild;

    tree.insert(20);


    // Checks the root node, and the tree size
    if (!tree.root.equals(node)) {
      fail("Error: Root was altered!");
    } else if (tree.size != 7) {
      fail("Error: Tree size was adjusted");
    }


    // Checks the colors of the Nodes
    if (!tree.root.rightChild.isBlack) {
      fail("Error: Node 20 should not be Red");
    } else if (tree.root.rightChild.rightChild.isBlack) {
      fail("Error: Node 23 should be Red");
    } else if (tree.root.rightChild.leftChild.isBlack) {
      fail("Error: Node 18 should be Red");
    }

    // Makes sure 20 is now the parent of Node18 & Node23 & Node20 is in the correct position in the
    // RBT Tree
    if (!tree.root.rightChild.leftChild.equals(node18)) {
      fail("Error: Node 18 is not the child of Node20");
    } else if (!tree.root.rightChild.rightChild.equals(node23)) {
      fail("Error: Node 23 is not the child of Node20");
    }

  }

  /**
   * This tests the functionality of the enforceRBTreePropertiesAfterInsert on a cascading fix where
   * a certain fix causes another violation further up the tree.
   * 
   * Tests with a red child, red parent, red siblins, and black grandparent, and red
   * great-grandparent
   */
  @Test
  public void test4() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(14);
    tree.insert(7);
    tree.insert(20);
    tree.insert(1);
    tree.insert(11);
    tree.insert(18);
    tree.insert(25);
    tree.insert(23);
    tree.insert(29);

    RedBlackTree.Node<Integer> node = tree.root.rightChild;
    RedBlackTree.Node<Integer> node25 = tree.root.rightChild.rightChild;
    RedBlackTree.Node<Integer> node23 = tree.root.rightChild.rightChild.leftChild;
    RedBlackTree.Node<Integer> node29 = tree.root.rightChild.rightChild.rightChild;

    tree.insert(27);

    // Checks the root node, and the tree size
    if (!tree.root.equals(node)) {
      fail("Error: Root was altered!");
    } else if (tree.size != 10) {
      fail("Error: Tree size was adjusted incorrectly");
    }

    // Checks the colors of the Nodes
    if (!tree.root.isBlack) {
      fail("Error: Node 20 should not be Red");
    } else if (tree.root.rightChild.isBlack) {
      fail("Error: Node 25 should be Red");
    } else if (!tree.root.rightChild.leftChild.isBlack) {
      fail("Error: Node 23 should be Black");
    } else if (!tree.root.rightChild.rightChild.isBlack) {
      fail("Error: Node 29 should be Black");
    } else if (tree.root.rightChild.rightChild.leftChild.isBlack) {
      fail("Error: Node 27 should be Red");
    }

    if (!tree.root.rightChild.equals(node25)) {
      fail("Error: Node 25 is not the child of Node20!");
    } else if (!tree.root.rightChild.leftChild.equals(node23)) {
      fail("Error: Node 23 is supposed to be the child of Node25!");
    } else if (!tree.root.rightChild.rightChild.equals(node29)) {
      fail("Error: Node 29 is supposed to be the child of Node25!");
    }


  }



}
