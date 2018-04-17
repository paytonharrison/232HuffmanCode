package code;

/** Binary tree node implementation with character, frequency and pointers to children */
class HuffNode  {
	
	//stores character AND weight
	
	public char character;             // letter
	public int weight;                 // instances of this letter
	public HuffNode leftChild;         // this node's left child
	public HuffNode rightChild;        // this node's right child
	
	public HuffNode() {
		leftChild = rightChild = null; 
	}

	public HuffNode(char character, int frequency)
	{ 
		leftChild = rightChild = null; 
		this.character = character;
		this.weight = frequency;
	}
	
	public HuffNode(char character, int frequency, HuffNode l, HuffNode r)
	{ 
		leftChild = l; 
		rightChild = r; 
		this.character = character;
		this.weight = frequency;
	}

	
	public HuffNode(int weight, HuffNode l, HuffNode r) {
		leftChild = l; 
		rightChild = r; 
		this.weight = weight;
	}

	public boolean isLeaf()
	{ 
		return (leftChild == null) && (rightChild == null); 
	}
	
	public void displayNode()
	{ 
		System.out.print( character + "" + weight ); 
	}
}