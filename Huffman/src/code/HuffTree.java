package code;

class HuffTree
{ // A Huffman coding tree
	public HuffNode root; // Root of the tree

	public HuffTree(char character, int frequency)
	{ 
		root = new HuffNode(character, frequency); 
	}

	public HuffTree(int frequency, HuffTree tmp1, HuffTree tmp2) {
		root = new HuffNode(frequency, tmp1.root, tmp2.root);
	}	


	public int getWeight() // Weight of tree is weight of root
	{ 
		return root.weight; 
	}
}