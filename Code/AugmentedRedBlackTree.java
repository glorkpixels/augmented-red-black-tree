package rbt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AugmentedRedBlackTree extends RedBlackTree {
	private final int RED = 0;
	private final int BLACK = 1;
	private int count = 0;
	private int counter = 0;
	private int count1 = 0;
	RedBlackTree rbtt = new RedBlackTree();

	private class Node<I, A, B, C> {

		double key = -1, color = BLACK;
		I id;
		A age;
		B bdate;
		C scount;
		Node left = nil, right = nil, parent = nil;

		Node(final double key, final I id, final A age, final B bdate, final C scount) {

			this.key = key;
			this.id = id;
			this.age = age;
			this.bdate = bdate;
			this.scount = scount;
		}

		public C getScount() {
			return scount;
		}

		public void setScount(C scount) {
			this.scount = scount;
		}

		public B getBdate() {
			return bdate;
		}

		public void setBdate(B bdate) {
			this.bdate = bdate;
		}

		public double getKey() {
			return key;
		}

		public void setKey(double key) {
			this.key = key;
		}

		public int getId() {
			return (int) id;
		}

		public void setId(I id) {
			this.id = id;
		}

		public A getAge() {
			return age;
		}

		public void setAge(A age) {
			this.age = age;
		}
	}

	private final Node nil = new Node(-1, -1, -1, -1, -1);
	private Node root = nil;
	private Node search = new Node(-1, -1, -1, -1, -1);
	private int peoplecounter;

	public void printTree(Node node) {
		if (node == nil) {
			return;
		}
		findSmallerthanNode(node);
		printTree(node.left);
		System.out.print(((node.color == RED) ? "Color: Red " : "Color: Black ") + "Age " + node.key + " IDS: "
				+ node.id + " Parent: " + node.parent.key + " Age Yearwise " + node.key / 365.0 + " Smaller nodes: "
				+ node.scount + "\n");
		printTree(node.right);
	}

	private Node findNode(Node findNode, Node node) {
		if (root == nil) {
			return null;
		}

		if (findNode.key < node.key) {
			if (node.left != nil) {
				return findNode(findNode, node.left);
			}
		} else if (findNode.key > node.key) {
			if (node.right != nil) {
				return findNode(findNode, node.right);
			}
		} else if (findNode.key == node.key) {
			return node;
		}
		return null;
	}

	private void findID(Node findNode, Node node) {

		if (node == nil) {
			return;
		}

		if (node.getId() != findNode.getId()) {
			findID(findNode, node.left);
			findID(findNode, node.right);
		} else
			search = node;

	}

	private void insert(Node node) {
		Node temp = root;
		if (root == nil) {
			root = node;
			node.color = BLACK;
			node.parent = nil;
		} else {
			node.color = RED;
			while (true) {
				if (node.key < temp.key) {
					if (temp.left == nil) {
						temp.left = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.left;
					}
				} else if (node.key >= temp.key) {
					if (temp.right == nil) {
						temp.right = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.right;
					}
				}
			}
			fixTree(node);
		}
	}

	// Takes as argument the newly inserted node
	private void fixTree(Node node) {
		while (node.parent.color == RED) {
			Node uncle = nil;
			if (node.parent == node.parent.parent.left) {
				uncle = node.parent.parent.right;

				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.right) {
					// Double rotation needed
					node = node.parent;
					rotateLeft(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				// if the "else if" code hasn't executed, this
				// is a case where we only need a single rotation
				rotateRight(node.parent.parent);
			} else {
				uncle = node.parent.parent.left;
				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.left) {
					// Double rotation needed
					node = node.parent;
					rotateRight(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				// if the "else if" code hasn't executed, this
				// is a case where we only need a single rotation
				rotateLeft(node.parent.parent);
			}
		}
		root.color = BLACK;

	}

	void rotateLeft(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.right;
			} else {
				node.parent.right = node.right;
			}
			node.right.parent = node.parent;
			node.parent = node.right;
			if (node.right.left != nil) {
				node.right.left.parent = node;
			}
			node.right = node.right.left;
			node.parent.left = node;
		} else {// Need to rotate root
			Node right = root.right;
			root.right = right.left;
			right.left.parent = root;
			root.parent = right;
			right.left = root;
			right.parent = nil;
			root = right;
		}
	}

	void rotateRight(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}

			node.left.parent = node.parent;
			node.parent = node.left;
			if (node.left.right != nil) {
				node.left.right.parent = node;
			}
			node.left = node.left.right;
			node.parent.right = node;
		} else {// Need to rotate root
			Node left = root.left;
			root.left = root.left.right;
			left.right.parent = root;
			root.parent = left;
			left.right = root;
			left.parent = nil;
			root = left;
		}
	}

	// Deletes whole tree
	void deleteTree() {
		root = nil;
	}

	// Deletion Code .

	// This operation doesn't care about the new Node's connections
	// with previous node's left and right. The caller has to take care
	// of that.
	void transplant(Node target, Node with) {
		if (target.parent == nil) {
			root = with;
		} else if (target == target.parent.left) {
			target.parent.left = with;
		} else
			target.parent.right = with;
		with.parent = target.parent;
	}

	void deleteFixup(Node x) {
		while (x != root && x.color == BLACK) {
			if (x == x.parent.left) {
				Node w = x.parent.right;
				if (w.color == RED) {
					w.color = BLACK;
					x.parent.color = RED;
					rotateLeft(x.parent);
					w = x.parent.right;
				}
				if (w.left.color == BLACK && w.right.color == BLACK) {
					w.color = RED;
					x = x.parent;
					continue;
				} else if (w.right.color == BLACK) {
					w.left.color = BLACK;
					w.color = RED;
					rotateRight(w);
					w = x.parent.right;
				}
				if (w.right.color == RED) {
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.right.color = BLACK;
					rotateLeft(x.parent);
					x = root;
				}
			} else {
				Node w = x.parent.left;
				if (w.color == RED) {
					w.color = BLACK;
					x.parent.color = RED;
					rotateRight(x.parent);
					w = x.parent.left;
				}
				if (w.right.color == BLACK && w.left.color == BLACK) {
					w.color = RED;
					x = x.parent;
					continue;
				} else if (w.left.color == BLACK) {
					w.right.color = BLACK;
					w.color = RED;
					rotateLeft(w);
					w = x.parent.left;
				}
				if (w.left.color == RED) {
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.left.color = BLACK;
					rotateRight(x.parent);
					x = root;
				}
			}
		}
		x.color = BLACK;
	}

	public <I> void consoleUI() throws IOException {

		Scanner scan = new Scanner(System.in);

		summonreading();
		fixTheDegrees(root);
		rbtt.RBTchecklist();

		System.out.println();

		ARBTchecklist();

		while (true) {
			System.out.println("\n1.-INSERT:\n" + "2.-GETNUMSMALLER1:\n" + "3.-GETNUMSMALLER2:\n" + "4.-GETMAX:\n"
					+ "5.-GETMIN:\n" + "6.-GETNUM\n" + "7.-PRINT:\n");

			count = 0;
			System.out.print("Choise: ");
			int choice = scan.nextInt();

			int item;
			Node node;
			switch (choice) {
			case 1:
				System.out.print("Please enter ID,dd/MM/yyyy format: ");
				String line = scan.next();

				rbtt.case1(line);

				Inserter(line);
				break;
			case 2:
				String entry = null;
				System.out.print("Please enter dd/MM/yyyy format: ");
				try {

					entry = scan.next();
					System.out.println("RBT OUTPUT");
					rbtt.case2(entry);
					System.out.println("AUG-RBT OUTPUT");
					getNumSmaller1(entry);

				} catch (Exception e) {
					System.out.println("Please use designated format");
				}

				break;

			case 3:
				int ids = 0;
				System.out.print("Please enter a node ID: ");
				String wow = scan.next();
				try {
					ids = Integer.parseInt(wow);
					System.out.println("RBT OUTPUT");
					rbtt.case3(ids);
					System.out.println("AUG-RBT OUTPUT");
					getNumSmaller2(ids);
				} catch (Exception e) {
					System.out.println("Please use designated format");
				}

				break;
			case 4:
				Node manx = root;
				Node max = treeMaximum(manx);

				double doubled = (double) max.getAge() / 365.0;
				int ageoldmax = (int) doubled;
				System.out.println("RBT OUTPUT");
				rbtt.case4();
				System.out.println("AUG-RBT OUTPUT");
				System.out.println("The maximum age of all people is" + ageoldmax + ", ID: " + max.getId()
						+ " and birthdate :" + max.getBdate());

				break;
			case 5:

				Node temper = root;
				Node min = treeMinimum(temper);

				double doubled2 = (double) min.getAge() / 365.0;
				int ageoldmin = (int) doubled2;
				System.out.println("RBT OUTPUT");
				rbtt.case5();
				System.out.println("AUG-RBT OUTPUT");
				System.out.println("The maximum age of all people is" + ageoldmin + ", ID: " + min.getId()
						+ " and birthdate :" + min.getBdate());
				break;
			case 6:
				peoplecounter = 0;
				Node hmpitct = root;
				System.out.println("RBT OUTPUT");
				rbtt.case6();
				System.out.println("AUG-RBT OUTPUT");
				howmanyPeopleinTree(hmpitct);
				System.out.println("The number of all people is " + peoplecounter);

				break;
			case 7:
				System.out.println("AUG RBT PRINT:");
				printTree(root);
				rbtt.case7();
				break;

			default:
				System.out.println("Wrong input/ please use listed inputs");
				break;

			}
		}
	}

	private void ReadDoc(String file) throws IOException {
		File openFile = new File(file);
		FileReader fr = new FileReader(openFile);
		BufferedReader br = new BufferedReader(fr);
		String line;// buffered reader

		try {

			while ((line = br.readLine()) != null) {

				String[] words = line.split(",");
				String aydi = words[0];
				String dateof = words[1];
				try {
					int id = Integer.parseInt(aydi);
					double age = AgeFinder(dateof);
					Node node = new Node(age, id, (Object) age, (Object) dateof, -1);
					insert(node);
//					System.out.println(node.getKey() + " " + node.getId() + " " + node.getAge());

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			System.out.println("AUG-RBT OUTPUT");
			System.out.println(file + " file read!");

		} catch (IOException e) {
			e.printStackTrace();
		}
		fr.close();
		br.close();
	}

	private void Inserter(String readline) throws IOException {

		String line = readline;// buffered reader
		String aydi = "";
		String dateof = "";
		try {

			String[] words = readline.split(",");
			if (words.length == 2) {
				aydi = words[0];
				dateof = words[1];
				String[] datestruk = dateof.split("/");
				if (datestruk.length == 3) {
					try {
						int id = Integer.parseInt(aydi);
						double age = AgeFinder(dateof);
						Node node = new Node(age, id, (Object) age, (Object) dateof, -1);
						System.out.println("AUG-RBT OUTPUT");
						insert(node);
						System.out.println(node.getId() + " " + node.getAge());
						System.out.println("Inserted");
					} catch (Exception e) {
						System.out.println("Wrong input style.You must use date like this dd/MM/yyyy");
					}
				} else {
					System.out.println("Wrong input style.You must use date like this dd/MM/yyyy");
				}

			} else {
				System.out.println("Wrong input style. You must use ID,dd/MM/yyyy");
			}
//			System.out.println(file + " file read!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getNumSmaller1(String entry) {
		count = 0;
		double proc = AgeFinder(entry);
		Node temp = root;

		RecursiveFindbyAge(temp, proc);

		System.out.println("The result of GETNUMSMALLER1 for the node with " + entry + " is " + count);
	}

	private void getNumSmaller2(int id) {
		Node firstnode = root;
		Node tempnode = new Node(-1, id, -1, -1, -1);
		findID(tempnode, firstnode);
		Node retVal = search;
		firstnode = root;
//		count = 0;
//		RecursiveFindbyAge(firstnode, (double) retVal.getAge());

		peoplecounter = 0;
		howmanyPeopleinTree(root);
		int fix = peoplecounter;
		int gets1 = (int) retVal.getScount() + 1;
		int inal = fix - gets1;
		System.out.println("The result of GETNUMSMALLER2 for the node with " + id + " is " + inal);

	}

	private void RecursiveFindbyAge(Node temp, double age) {

		if ((double) temp.age > age) {
			count++;
			if (temp.left != nil) {
				RecursiveFindbyAge(temp.left, age);
			}
			if (temp.right != nil) {
				RecursiveFindbyAge(temp.right, age);
			}

		} else {

			if (temp.right != nil) {

				RecursiveFindbyAge(temp.right, age);
			}
		}
	}

	private void RecursiveFindSmallerNodes(Node temp, double age) {

		if ((double) temp.age < age) {
			count++;
			if (temp.left != nil) {
				RecursiveFindSmallerNodes(temp.left, age);
			}
			if (temp.right != nil) {
				RecursiveFindSmallerNodes(temp.right, age);
			}
			// ((double) temp.age >(double) age)
		} else {
			if (temp.left != nil) {
				RecursiveFindSmallerNodes(temp.left, age);
			}
		}
	}

	private double AgeFinder(String dateold) {

		// 09/28/2015
		String today = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateBeforeString = dateold;
		String dateAfterString = today;
		double yearold = 0;

		try {

			Date dateBefore = myFormat.parse(dateBeforeString);
			Date dateAfter = myFormat.parse(dateAfterString);
			long difference = dateAfter.getTime() - dateBefore.getTime();
			float daysBetween = (difference / (1000 * 60 * 60 * 24));
			float daysBetweens = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
			yearold = (int) daysBetweens;

//			       System.out.println("Number of Days between dates: "+daysBetween);
//			System.out.println(yearold);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("age finder outor :(");
		}
		return yearold;

	}

	private Node treeMinimum(Node subTreeRoot) {
		while (subTreeRoot.left != nil) {
			subTreeRoot = subTreeRoot.left;
		}
		return subTreeRoot;
	}

	private Node treeMaximum(Node subTreeRoot) {

		while (subTreeRoot.right != nil) {
			subTreeRoot = subTreeRoot.right;
		}

		return subTreeRoot;

	}

	private void howmanyPeopleinTree(Node rooter) {

		if (rooter == nil) {
			return;
		}
		peoplecounter++;
		howmanyPeopleinTree(rooter.left);
		howmanyPeopleinTree(rooter.right);

	}

	private void ARBTchecklist() {

		System.out.println("------------AUGMENTED - RED BLACK TREE----------- ");
		Node rookie = root;
		Node returner = new Node(-1, -1, -1, -1, -1);
		getNumSmaller1("19/8/2000");
		getNumSmaller2(1825);
		returner = treeMaximum(rookie);
		double doubled = (double) returner.getAge() / 365.0;
		int ageoldmax = (int) doubled;
		System.out.println("The maximum age of all people is " + ageoldmax + ", ID: " + returner.getId()
				+ " and birthdate :" + returner.getBdate());
		returner = treeMinimum(rookie);
		double doubled2 = (double) returner.getAge() / 365.0;
		int ageoldmin = (int) doubled2;

		System.out.println("The maximum age of all people is " + ageoldmin + ", ID: " + returner.getId()
				+ " and birthdate :" + returner.getBdate());

		peoplecounter = 0;
		howmanyPeopleinTree(rookie);
		System.out.println("The number of all people is " + peoplecounter + ".");
	}

	private void fixTheDegrees(Node node) {
		if (node == nil) {
			return;
		}
		findSmallerthanNode(node);
		fixTheDegrees(node.left);
		fixTheDegrees(node.right);
	}

	private void summonreading() {
		try {
			Scanner scan = new Scanner(System.in);
			System.out.print("Please enter document name:");
			String docname = scan.next();

			ReadDoc(docname);
			rbtt.case8(docname);

		} catch (Exception e) {
			System.out.println("Invalid document name try again:");
			summonreading();
		}
	}

	private void findSmallerthanNode(Node subTreeRoot) {
		if (subTreeRoot == nil) {
			return;
		}
		count = 0;

		RecursiveFindSmallerNodes(root, (double) subTreeRoot.getAge());
		subTreeRoot.scount = count;
//			findSmallerthanNode(subTreeRoot.left);		
//			findSmallerthanNode(subTreeRoot.right);	
	}

}