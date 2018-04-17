package code;

class PriorityQ
{
	// array in sorted order, from max at 0 to min at size-1
	private int maxSize;
	private HuffTree[] queArray;
	private int nItems;
	//-------------------------------------------------------------
	public PriorityQ(int s)
	{
		maxSize = s;
		queArray = new HuffTree[maxSize];
		nItems = 0;
	}
	//-------------------------------------------------------------
	public void insert(HuffTree item)
	{
		int j;

		if(nItems==0)                         // if no items,
			queArray[nItems++] = item;         // insert at 0
		else                                  // if items,
		{
			for(j=nItems-1; j>=0; j--)      // start at end,
			{                            // if new item larger,
				if( item.root.weight > queArray[j].root.weight )
					queArray[j+1] = queArray[j]; // shift upward
				else                            // if smaller,
					break;                       // done shifting
			}  // end for
			queArray[j+1] = item;              // insert it
			nItems++;
		}  // end else (nItems > 0)
	}  // end insert()
	//-------------------------------------------------------------
	public HuffTree remove()             // remove minimum item
	{ return queArray[--nItems]; }
	//-------------------------------------------------------------
	public HuffTree peekMin()            // peek at minimum item
	{ return queArray[nItems-1]; }
	//-------------------------------------------------------------
	public boolean isEmpty()             // true if queue is empty
	{ return (nItems==0); }
	//-------------------------------------------------------------
	public boolean isFull()               // true if queue is full
	{ return (nItems == maxSize); }
	//-------------------------------------------------------------
	public int getSize()
	{ return nItems; }
	//-------------------------------------------------------------
}  // end class PriorityQ