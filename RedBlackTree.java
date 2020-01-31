
import java.awt.Color;

/**
 * A simple red-black tree class.
 */
public class RedBlackTree {

	static sNode root;
	public static int count=0;

	/**
	 * Constructs an empty RedBlackTree that can only accept Comparables as items.
	 */
	public RedBlackTree() {
		root = null;
	}

	/**
	 * Constructs an empty RedBlackTree that orders its items according to the given
	 * comparator.
	 */

	/**
	 * The nodes in a red-black tree store a color together with the actual data in
	 * the node.
	 */

	/**
	 * Adds a single data item to the tree. If there is already an item in the tree
	 * that compares equal to the item being inserted, it is "overwritten" by the
	 * new item. Overrides BinarySearchTree.add because the tree needs to be
	 * adjusted after insertion.
	 */
	public void add(int element1, int element2) {
		if (root == null) {
			root = new sNode(element1, element2);
		}
		sNode n = root;
		while (true) {
			int comparisonResult = compare(element1, n.getBuildingNum_key_RBT());
			if (comparisonResult == 0) {
				return;
			} else if (comparisonResult < 0) {
				if (n.getLeft() == null) {
					n.setLeft(new sNode(element1, element2));
					n.getLeft().color = Color.red;
					n.getLeft().setParent(n);
					adjustAfterInsertion(n.getLeft());
					break;
				}
				n = n.getLeft();
			} else { // comparisonResult > 0
				if (n.getRight() == null) {
					n.setRight(new sNode(element1, element2));
					n.getRight().color = Color.red;
					n.getRight().setParent(n);
					adjustAfterInsertion(n.getRight());
					break;
				}
				n = n.getRight();
			}
		}
	}

	public void add(sNode node) {
		if (root == null) {
			root = node;
		}
		sNode n = root;
		while (true) {
			int comparisonResult = compare(node.getBuildingNum_key_RBT(), n.getBuildingNum_key_RBT());
			if (comparisonResult == 0) {
				return;
			} else if (comparisonResult < 0) {
				if (n.getLeft() == null) {
					n.setLeft(node);
					n.getLeft().color = Color.red;
					n.getLeft().setParent(n);
					adjustAfterInsertion(n.getLeft());
					break;
				}
				n = n.getLeft();
			} else { // comparisonResult > 0
				if (n.getRight() == null) {
					n.setRight(node);
					n.getRight().color = Color.red;
					n.getRight().setParent(n);
					adjustAfterInsertion(n.getRight());
					break;
				}
				n = n.getRight();
			}
		}
	}

	/**
	 * Removes the node containing the given value. Does nothing if there is no such
	 * node.
	 */
	public void remove(sNode n) {
		if (n == null) {
			// No such object, do nothing.
			return;
		} else if (n.getLeft() != null && n.getRight() != null) {
			// Node has two children, Copy predecessor data in.
			// sNode predecessor = predecessor(n);
			int k = 0;
			sNode predecessor = predecessor(n);
			
			if (predecessor.getParent().getRight() == predecessor) {

				sNode leaf = predecessor.getLeft();
				if (leaf != null)
					leaf.setParent(null);

				predecessor.setLeft(null);

				predecessor.getParent().setRight(null);

				sNode leafparent = predecessor.getParent();
				predecessor.setParent(null);
				leafparent.setRight(null);

		
				if (n.getParent() != null && n.getParent().getRight() == n) {
					n.getParent().setRight(predecessor);
				} else if (n.getParent() != null && n.getParent().getLeft() == n) {

					n.getParent().setLeft(predecessor);
				}

				predecessor.setParent(n.getParent());

				predecessor.setRight(n.getRight());
				predecessor.getRight().setParent(predecessor);

				predecessor.setLeft(n.getLeft());
				predecessor.getLeft().setParent(predecessor);

				n.setRight(null);
				n.setParent(leafparent);
				n.setLeft(null);

				leafparent.setRight(n);
				if (leaf != null)
					leaf.setParent(n);
				n.setLeft(leaf);

				if (predecessor.getParent() == null) {
					root = predecessor;

				}
			} else {
				predecessor.getParent().setLeft(null);
				predecessor.setParent(predecessor.getParent().getParent());
				if (n.getParent() != null && n.getParent().getRight() == n) {
					n.getParent().setRight(predecessor);
				} else if (n.getParent() != null && n.getParent().getLeft() == n) {

					n.getParent().setLeft(predecessor);
				}
				predecessor.setRight(n.getRight());
				predecessor.getRight().setParent(predecessor);
				n.setParent(predecessor);
				n.setRight(null);
				n.setLeft(null);
				if (predecessor.getParent() == null) {
					root = predecessor;
				}
			}
			Color temp = n.color;
			predecessor.color = n.color;
			n.color = temp;
		}

		// At this point node has zero or one child
		sNode pullUp = leftOf(n) == null ? rightOf(n) : leftOf(n);
		if (pullUp != null) {
			// Splice out node, and adjust if pullUp is a double black.
			if (n == root) {
				setRoot(pullUp);
			} else if (n.getParent().getLeft() == n) {
				n.getParent().setLeft(pullUp);
				n.getParent().getLeft().setParent(n.getParent());
			} else {
				n.getParent().setRight(pullUp);
				n.getParent().getRight().setParent(n.getParent());
			}
			if (isBlack(n)) {
				adjustAfterRemoval(pullUp);
			}
		} else if (n == root) {
			// Nothing to pull up when deleting a root means we emptied the tree
			setRoot(null);
		} else {
			// The node being deleted acts as a double black sentinel
			if (isBlack(n)) {
				adjustAfterRemoval(n);
			}
			if (n.getParent() != null) {
				if (n.getParent().getLeft() == n) {
					n.getParent().setLeft(null);
				} else if (n.getParent().getRight() == n) {
					n.getParent().setRight(null);
				}
				n.setParent(null);
			}
		}
	}

	/**
	 * Classic algorithm for fixing up a tree after inserting a node.
	 */
	private void adjustAfterInsertion(sNode n) {
		// Step 1: color the node red
		setColor(n, Color.red);

		// Step 2: Correct double red problems, if they exist
		if (n != null && n != root && isRed(parentOf(n))) {

			// Step 2a (simplest): Recolor, and move up to see if more work
			// needed
			if (isRed(siblingOf(parentOf(n)))) {
				setColor(parentOf(n), Color.black);
				setColor(siblingOf(parentOf(n)), Color.black);
				setColor(grandparentOf(n), Color.red);
				adjustAfterInsertion(grandparentOf(n));
			}

			// Step 2b: Restructure for a parent who is the left child of the
			// grandparent. This will require a single right rotation if n is
			// also
			// a left child, or a left-right rotation otherwise.
			else if (parentOf(n) == leftOf(grandparentOf(n))) {
				if (n == rightOf(parentOf(n))) {
					rotateLeft(n = parentOf(n));
				}
				setColor(parentOf(n), Color.black);
				setColor(grandparentOf(n), Color.red);
				rotateRight(grandparentOf(n));
			}

			//  Restructure for a parent who is the right child of the
			// grandparent. This will require a single left rotation if n is
			// also
			// a right child, or a right-left rotation otherwise.
			else if (parentOf(n) == rightOf(grandparentOf(n))) {
				if (n == leftOf(parentOf(n))) {
					rotateRight(n = parentOf(n));
				}
				setColor(parentOf(n), Color.black);
				setColor(grandparentOf(n), Color.red);
				rotateLeft(grandparentOf(n));
			}
		}

		// Step 3: Color the root black
		setColor(root, Color.black);
	}

	/*
	 * Adjust After Removal
	 */
	private void adjustAfterRemoval(sNode n) {
		while (n != root && isBlack(n)) {
			if (n == leftOf(parentOf(n))) {
				// Pulled up node is a left child
				sNode sibling = rightOf(parentOf(n));
				if (isRed(sibling)) {
					setColor(sibling, Color.black);
					setColor(parentOf(n), Color.red);
					rotateLeft(parentOf(n));
					sibling = rightOf(parentOf(n));
				}
				if (isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
					setColor(sibling, Color.red);
					n = parentOf(n);
				} else {
					if (isBlack(rightOf(sibling))) {
						setColor(leftOf(sibling), Color.black);
						setColor(sibling, Color.red);
						rotateRight(sibling);
						sibling = rightOf(parentOf(n));
					}
					setColor(sibling, colorOf(parentOf(n)));
					setColor(parentOf(n), Color.black);
					setColor(rightOf(sibling), Color.black);
					rotateLeft(parentOf(n));
					n = root;
				}
			} else {
				// pulled up node is a right child
				sNode sibling = leftOf(parentOf(n));
				if (isRed(sibling)) {
					setColor(sibling, Color.black);
					setColor(parentOf(n), Color.red);
					rotateRight(parentOf(n));
					sibling = leftOf(parentOf(n));
				}
				if (isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
					setColor(sibling, Color.red);
					n = parentOf(n);
				} else {
					if (isBlack(leftOf(sibling))) {
						setColor(rightOf(sibling), Color.black);
						setColor(sibling, Color.red);
						rotateLeft(sibling);
						sibling = leftOf(parentOf(n));
					}
					setColor(sibling, colorOf(parentOf(n)));
					setColor(parentOf(n), Color.black);
					setColor(leftOf(sibling), Color.black);
					rotateRight(parentOf(n));
					n = root;
				}
			}
		}
		setColor(n, Color.black);
	}


// Function names are self explainatory
	private Color colorOf(sNode n) {
		return n == null ? Color.black : n.color;
	}

	private boolean isRed(sNode n) {
		return n != null && colorOf(n) == Color.red;
	}

	private boolean isBlack(sNode n) {
		return n == null || colorOf(n) == Color.black;
	}

	private void setColor(sNode n, Color c) {
		if (n != null)
			n.color = c;
	}

	private sNode parentOf(sNode n) {
		return n == null ? null : n.getParent();
	}

	private sNode grandparentOf(sNode n) {
		return (n == null || n.getParent() == null) ? null : n.getParent().getParent();
	}

	private sNode siblingOf(sNode n) {
		return (n == null || n.getParent() == null) ? null
				: (n == n.getParent().getLeft()) ? n.getParent().getRight() : n.getParent().getLeft();
	}

	private sNode leftOf(sNode n) {
		return n == null ? null : n.getLeft();
	}

	private sNode rightOf(sNode n) {
		return n == null ? null : n.getRight();
	}

	int compare(int a, int b) {

		if (a == b) {
			return 0;
		} else if (a > b) {
			return 1;
		} else
			return -1;
	}

	public sNode predecessor(sNode n1) {
		sNode n2 = n1.getLeft();
		while (n2.getRight() != null) {

			n2 = n2.getRight();

		}

		return n2;

	}

	private void rotateLeft(sNode s) {

		if (s != null && s.getParent() != null) {
			if (s.getParent().getLeft() == s) {

				s.getParent().setLeft(s.getRight());
			} else if (s.getParent().getRight() == s) {
				s.getParent().setRight(s.getRight());

			}
		} else if (s != null) {

			root = s.getRight();
			root.setParent(null);

		}

		if (s != null && s.getRight() != null) {
			sNode temp = s.getRight().getLeft();
			s.getRight().setLeft(s);
			s.getRight().setParent(s.getParent());
			s.setParent(s.getRight());
			s.setRight(null);
			if (temp != null) {
				s.setRight(temp);
				s.getRight().setParent(s);

			}
		}

	}

	private void rotateRight(sNode s) {

		if (s != null && s.getParent() != null) {
			if (s.getParent().getRight() == s) {

				s.getParent().setRight(s.getLeft());
			} else if (s.getParent().getLeft() == s) {
				s.getParent().setLeft(s.getLeft());

			}
		} else if (s != null) {

			root = s.getLeft();
			root.setParent(null);

		}

		if (s != null && s.getLeft() != null) {
			sNode temp = s.getLeft().getRight();
			s.getLeft().setRight(s);
			s.getLeft().setParent(s.getParent());

			s.setParent(s.getLeft());
			s.setLeft(null);
			if (temp != null) {
				s.setLeft(temp);
				s.getLeft().setParent(s);
			}
		}
	}

	public void printall() {

		sNode k = root;
		printall(k);

	}

	public sNode[] printall(sNode k) {
		if (k == null) {
			return null;
		}

		sNode[] array = new sNode[2000];
		int c = 0;
		if (k.getLeft() == null && k.getRight() != null) {
			System.out.println("PARENT: " + k.getBuildingNum_key_RBT() + k.color + "   RIGHT CHILD:  "
					+ k.getRight().getBuildingNum_key_RBT() + k.getRight().color);
			array[c++] = k;
		} else if (k.getLeft() != null && k.getRight() == null) {
			System.out.println("PARENT: " + k.getBuildingNum_key_RBT() + k.color + "   LEFT CHILD:  "
					+ k.getLeft().getBuildingNum_key_RBT() + k.getLeft().color);
			array[c++] = k;

		} else if (k.getLeft() == null && k.getRight() == null) {
			System.out.println("PARENT: " + k.getBuildingNum_key_RBT() + k.color.toString());
			array[c++] = k;

		} else {
			System.out.println("PARENT: " + k.getBuildingNum_key_RBT() + k.color + "   LEFT CHILD:  "
					+ k.getLeft().getBuildingNum_key_RBT() + k.getLeft().color + "   RIGHT CHILD:  "
					+ k.getRight().getBuildingNum_key_RBT() + k.getLeft().color);
			array[c++] = k;
		}
		printall(k.getLeft());
		printall(k.getRight());

		return array;

	}

	public void print(int buildingnumber) {

		sNode k = root;

		while (k != null) {

			if (buildingnumber > k.getBuildingNum_key_RBT()) {

				k = k.getRight();
			} else if (buildingnumber < k.getBuildingNum_key_RBT()) {

				k = k.getLeft();

			} else if (buildingnumber == k.getBuildingNum_key_RBT()) {

				System.out.println("(" + k.getBuildingNum_key_RBT() + "," + k.getExecuted_time_key_MH() + ","
						+ k.getTotal_time() + ")");
				return;
			}
		}

		System.out.println("(0,0,0)");

	}

	public void setRoot(sNode n) {
		root = n;
	}

	public void print(int buildingnumber1, int buildingnumber2) {

		sNode k = root;
		traverse(k, buildingnumber1, buildingnumber2);
		if(count!=0)
		System.out.println();
		else
		System.out.println("(0,0,0)");
	}

	public void traverse(sNode k, int buildingnumber1, int buildingnumber2) {

		if (k == null)
			{
			return;
			
			}
                
		if (k.getBuildingNum_key_RBT() <= buildingnumber2 && k.getBuildingNum_key_RBT() >= buildingnumber1) {
			traverse(k.getLeft(), buildingnumber1, buildingnumber2);
			if(k.getLeft()!=null&&k.getLeft().getBuildingNum_key_RBT()>=buildingnumber1) {
				System.out.print(",");
			}

			System.out.print("(" + k.getBuildingNum_key_RBT() + "," + k.getExecuted_time_key_MH() + ","
					+ k.getTotal_time() + ")");
			count++;
            if(k.getRight()!=null&&k.getRight().getBuildingNum_key_RBT()<=buildingnumber2) {
            	
            	System.out.print(",");
            }
			
			traverse(k.getRight(), buildingnumber1, buildingnumber2);

		} else if (k.getBuildingNum_key_RBT() > buildingnumber2) {
			traverse(k.getLeft(), buildingnumber1, buildingnumber2);

		} else if (k.getBuildingNum_key_RBT() < buildingnumber1) {

			traverse(k.getRight(), buildingnumber1, buildingnumber2);

		}
            
	}
    // For Testing Only 
	public static void main(String[] arg) {
		System.out.println("The Red Black is ");
		RedBlackTree RBT = new RedBlackTree();
		RBT.add(1, 5);
		RBT.add(2, 3);
		RBT.add(7, 17);
		RBT.add(8, 10);
		RBT.add(4, 84);
		RBT.add(5, 19);
		RBT.add(6, 6);
		RBT.add(3, 22);
		// RBT.add(9,9);
		// RBT.minHeap();

		RBT.printall();
		RBT.print(2, 6);
		RBT.print(8);

		RBT.remove(root);
		RBT.printall();

	}
}
