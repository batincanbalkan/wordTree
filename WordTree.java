//Batin Can Balkan
package A3;
import java.util.*;

/*
 *  WordTree class.  Each node is associated with a prefix of some word 
 *  stored in the tree.   (Any string is a prefix of itself.)
 */

public class WordTree {
	WordTreeNode root;

	// Empty tree has just a root node. All the children are null.

	public WordTree() {
		root = new WordTreeNode();
	}

	/*
	 * Insert word into the tree. First, find the longest prefix of word that is
	 * already in the tree (use getPrefixNode() below). Then, add TreeNode(s) such
	 * that the word is inserted according to the specification in PDF.
	 */
	public void insert(String word) {
		// ADD YOUR CODE BELOW HERE
		WordTreeNode start = getPrefixNode(word);
		WordTreeNode next = start;

		int indexNextToAdd = start.depth;
		if (word.length() == start.depth) { // the longest prefix in the tree is as long as the word aka the word
											// has already been added to the tree
			start.endOfWord = true; // make the tree recognize that the prefix is also a word
		} else {
			for (int i = indexNextToAdd; i < word.length(); i++) {
				WordTreeNode newChar = next.createChild(word.charAt(i));
				if (i == word.length() - 1) {
					newChar.setEndOfWord(true);
				}
				next = newChar;
			}
		}

		// ADD YOUR ABOVE HERE
	}

	// insert each word in a given list

	public void loadWords(ArrayList<String> words) {
		for (int i = 0; i < words.size(); i++) {
			insert(words.get(i));
		}
		return;
	}

	/*
	 * Given an input word, return the TreeNode corresponding the longest prefix
	 * that is found. If no prefix is found, return the root. In the example in the
	 * PDF, running getPrefixNode("any") should return the dashed node under "n",
	 * since "an" is the longest prefix of "any" in the tree.
	 */
	WordTreeNode getPrefixNode(String word) {
		// ADD YOUR CODE BELOW HERE
		char letter;
		WordTreeNode prefixNode = root;
		WordTreeNode nextPrefixNode;
		for (int i = 0; i < word.length(); i++) {
			letter = word.charAt(i);
			nextPrefixNode = prefixNode.children[letter];
			if (nextPrefixNode != null) {
				prefixNode = nextPrefixNode;
			} else
				break;
		}
		return prefixNode; // REPLACE THIS STUB

		// ADD YOUR CODE ABOVE HERE

	}

	/*
	 * Similar to getPrefixNode() but now return the prefix as a String, rather than
	 * as a TreeNode.
	 */

	public String getPrefix(String word) {
		return getPrefixNode(word).toString();
	}

	/*
	 * Return true if word is contained in the tree (i.e. it was added by insert),
	 * false otherwise. Hint: any string is a prefix of itself, so you can use
	 * getPrefixNode().
	 */
	public boolean contains(String word) {
		// ADD YOUR CODE BELOW HERE
		if ((getPrefixNode(word).toString().length() == word.length()) && getPrefixNode(word).endOfWord) {
			return true;
		} else
			return false;

		// ADD YOUR CODE ABOVE HERE
	}

	// method ressembling the above contains exept checks for the exact input string
	// and returns true if it is present in the tree regardless if it is an actual
	// word or not
	public boolean containString(String word) {
		// ADD YOUR CODE BELOW HERE
		if ((getPrefixNode(word).toString().length() == word.length())) {
			return true;
		} else
			return false;

		// ADD YOUR CODE ABOVE HERE
	}

	/*
	 * Return a list of all words in the tree that have the given prefix. For
	 * example, getListPrefixMatches(" ") should return all words in the tree.
	 */

	// 3h til this method
	public ArrayList<String> getListPrefixMatches(String prefix) {
		// ADD YOUR CODE BELOW
		ArrayList<String> wordList = new ArrayList<String>();
		ArrayList<WordTreeNode> nodeList = new ArrayList<WordTreeNode>();
		 if(!containString(prefix)) {
		 wordList.add("");
		 }else {
		WordTreeNode prefixNode = getPrefixNode(prefix);

		if (prefixNode.endOfWord) {
			nodeList.add(prefixNode);
		}
		if ((prefixNode == root) && prefix.charAt(0) == (char) 0) { // given prefix was blankspace
			scanEndWord(prefixNode, nodeList);
		} else if (prefixNode == root) { // prefix node is the root but the given prefix was not the blankspace
			nodeList.add(prefixNode); // add the blankspace character
		} else {
			scanEndWord(prefixNode, nodeList); // general condition when the prefix node isnt the root node
		}

		for (WordTreeNode e : nodeList) {
			wordList.add(e.toString());
		}

		 }
		return wordList;
	}

	// ADD YOUR CODE ABOVE HERE

	// recursively scans through the tree and finds nodes that have endOfWord = true
	// and adds them to array list
	public void scanEndWord(WordTreeNode node, ArrayList<WordTreeNode> nodeList) {

		if (node != null && node.children != null) {
			for (WordTreeNode n : node.children) {
				if (n == null) {
					continue;
				} else if (n.endOfWord) {
					nodeList.add(n);
				}
				scanEndWord(n, nodeList);
			}
		}

	}

	/*
	 * Below is the inner class defining a node in a Tree (prefix) tree. A node
	 * contains an array of children: one for each possible character. The children
	 * themselves are nodes. The i-th slot in the array contains a reference to a
	 * child node which corresponds to character (char) i, namely the character with
	 * ASCII (and UNICODE) code value i. Similarly the index of character c is
	 * obtained by "casting": (int) c. So children[97] = children[ (int) 'a'] would
	 * reference a child node corresponding to 'a' since (char)97 == 'a' and
	 * (int)'a' == 97.
	 * 
	 * To learn more: -For all unicode charactors, see http://unicode.org/charts in
	 * particular, the ascii characters are listed at
	 * http://unicode.org/charts/PDF/U0000.pdf -For ascii table, see
	 * http://www.asciitable.com/ -For basic idea of converting (casting) from one
	 * type to another, see any intro to Java book (index
	 * "primitive type conversions"), or google that phrase. We will cover casting
	 * of reference types when get to the Object Oriented Design part of this
	 * course.
	 */

	public class WordTreeNode {
		/*
		 * Highest allowable character index is NUMCHILDREN-1 (assuming one-byte ASCII
		 * i.e. "extended ASCII")
		 * 
		 * NUMCHILDREN is constant (static and final) To access it, write
		 * "TreeNode.NUMCHILDREN"
		 * 
		 * For simplicity, we have given each WordTree node 256 children. Note that if
		 * our words only consisted of characters from {a,...,z,A,...,Z} then we would
		 * only need 52 children. The WordTree can represent more general words e.g. it
		 * could also represent many special characters often used in passwords.
		 */

		public static final int NUMCHILDREN = 256;

		WordTreeNode parent;
		WordTreeNode[] children;
		int depth; // 0 for root, 1 for root's children, 2 for their children, etc..

		char charInParent; // Character associated with the tree edge from this node's parent
							// to this node.
		// See comment above for relationship between an index in 0 to 255 and a char
		// value.

		boolean endOfWord; // Set to true if prefix associated with this node is also a word.

		// Constructor for new, empty node with NUMCHILDREN children.
		// All the children are automatically initialized to null.

		public WordTreeNode() {
			children = new WordTreeNode[NUMCHILDREN];

			// These assignments below are unnecessary since they are just the default
			// values.

			endOfWord = false;
			depth = 0;
			charInParent = (char) 0;
		}

		/*
		 * Add a child to current node. The child is associated with the character
		 * specified by the method parameter. Make sure you set as many fields in the
		 * child node as you can.
		 * 
		 * To implement this method, see the comment above the inner class TreeNode
		 * declaration.
		 * 
		 */

		public WordTreeNode createChild(char c) {
			// ADD YOUR CODE BELOW HERE
			WordTreeNode child = new WordTreeNode();
			child.children = new WordTreeNode[WordTreeNode.NUMCHILDREN];
			child.parent = this;
			child.depth = this.depth + 1;
			child.charInParent = c;
			child.endOfWord = false; //
			this.children[(int) c] = child;
			// ADD YOUR CODE ABOVE HERE

			return child;
		}

		// Get the child node associated with a given character, i.e. that character is
		// "on" the edge from this node to the child. The child could be null.

		public WordTreeNode getChild(char c) {
			return this.children[c];
		}

		// Test whether the path from the root to this node is a word in the tree.
		// Return true if it is, false if it is prefix but not a word.

		public boolean isEndOfWord() {
			return endOfWord;
		}

		// Set to true for the node associated with the last character of an input word

		public void setEndOfWord(boolean endOfWord) {
			this.endOfWord = endOfWord;
		}

		/*
		 * Return the prefix (as a String) associated with this node. This prefix is
		 * defined by descending from the root to this node. However, you will find it
		 * is easier to implement by ascending from the node to the root, composing the
		 * prefix string from its last character to its first.
		 *
		 * This overrides the default toString() method.
		 */

		@Override
		public String toString() {
			// ADD YOUR CODE BELOW HERE
			String prefix2 = new String();
			WordTreeNode parent = this.parent;

			prefix2 = prefix2 + this.charInParent;
			while (parent != null && parent.charInParent != (char) 0) {
				prefix2 = parent.charInParent + prefix2;
				WordTreeNode temp = parent;
				parent = temp.parent;
			}
			return prefix2;

			// ADD YOUR CODE ABOVE HERE
		}
	}

}