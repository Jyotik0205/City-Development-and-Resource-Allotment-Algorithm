



public class MinHeap { 
    private sNode[] Heap; 
    private int size; 
    public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	private int maxsize; 
  
    private static final int FRONT = 1; 
  
    public MinHeap(int maxsize) 
    { 
        this.maxsize = maxsize; 
        this.size = 0; 
        Heap = new sNode[this.maxsize+1]; // +1 because of head node
        sNode head=new sNode(Integer.MAX_VALUE,0);
        head.setExecuted_time_key_MH(Integer.MAX_VALUE);
        Heap[0]=head;
    } 
    
    public sNode returnmin() {
    	sNode a=Heap[FRONT];
    	
    	return a;
    }
  
    // Function to return the position of 
    // the parent for the node currently 
    // at pos 
    private int parent(int pos) 
    { 
        return pos / 2; 
    } 
  
    // Function to return the position of the 
    // left child for the node currently at pos 
    private int leftChild(int pos) 
    { 
        return (2 * pos); 
    } 
  
    // Function to return the position of 
    // the right child for the node currently 
    // at pos 
    private int rightChild(int pos) 
    { 
    	if(2*pos+1<=size) {
        return (2 * pos) + 1;
    	}else
    		return 0;
    } 
  
    // Function that returns true if the passed 
    // node is a leaf node 
    private boolean isLeaf(int pos) 
    { 
        if (pos > (size / 2) && pos <= size) { 
            return true; 
        } 
        return false; 
    } 
  
    // Function to swap two nodes of the heap 
    private void swap(int fpos, int spos) 
    { 
        sNode tmp; 
        tmp = Heap[fpos]; 
        Heap[fpos] = Heap[spos]; 
        Heap[spos] = tmp; 
    } 
  
    // Function to heapify the node at pos 
    private void minHeapify(int pos) 
    { 
  
        // If the node is a non-leaf node and greater 
        // than any of its child 
        if (!isLeaf(pos)) { 
            if (Heap[pos].getExecuted_time_key_MH() > Heap[leftChild(pos)].getExecuted_time_key_MH()
                || Heap[pos].getExecuted_time_key_MH() > Heap[rightChild(pos)].getExecuted_time_key_MH()) { 
  
                // Swap with the left child and heapify 
                // the left child 
                if (Heap[leftChild(pos)].getExecuted_time_key_MH() < Heap[rightChild(pos)].getExecuted_time_key_MH()) {
                	
                	
                		swap(pos, leftChild(pos)); 
                        minHeapify(leftChild(pos)); 
               } 
  
                // Swap with the right child and heapify 
                // the right child 
                else { 
                    swap(pos, rightChild(pos)); 
                    minHeapify(rightChild(pos)); 
                } 
                
                
            } 
            
            if(Heap[pos].getExecuted_time_key_MH() == Heap[leftChild(pos)].getExecuted_time_key_MH()
                    || Heap[pos].getExecuted_time_key_MH() == Heap[rightChild(pos)].getExecuted_time_key_MH()) {
            	
            	{
            		if (Heap[pos].getExecuted_time_key_MH() == Heap[leftChild(pos)].getExecuted_time_key_MH()&&Heap[pos].getExecuted_time_key_MH() == Heap[rightChild(pos)].getExecuted_time_key_MH()) {
            			
            			// Condition to handle the situation of same executed time 
            			 if (Heap[pos].getBuildingNum_key_RBT() >= Heap[leftChild(pos)].getBuildingNum_key_RBT()
            		                || Heap[pos].getBuildingNum_key_RBT() >= Heap[rightChild(pos)].getBuildingNum_key_RBT()) { 
            		  
            		                // Swap with the left child and heapify 
            		                // the left child 
            		                if (Heap[leftChild(pos)].getBuildingNum_key_RBT() < Heap[rightChild(pos)].getBuildingNum_key_RBT()) {
            		                	
            		                	
            		                		swap(pos, leftChild(pos)); 
            		                        minHeapify(leftChild(pos)); 
            		               } 
            		  
            		                // Swap with the right child and heapify 
            		                // the right child 
            		                else { 
            		                    swap(pos, rightChild(pos)); 
            		                    minHeapify(rightChild(pos)); 
            		                } 
            		                
            		                
            		            }
                    } else {
                    	
                    	if (Heap[pos].getExecuted_time_key_MH() == Heap[leftChild(pos)].getExecuted_time_key_MH()) {
                    		
                    		if(Heap[pos].getBuildingNum_key_RBT() > Heap[leftChild(pos)].getBuildingNum_key_RBT()) {
                    			
                    			swap(pos, leftChild(pos)); 
    		                    minHeapify(leftChild(pos)); 

                    		}
                    		
                    	}
                    	else {
                    		
	                            if(Heap[pos].getBuildingNum_key_RBT() > Heap[rightChild(pos)].getBuildingNum_key_RBT()) {
                    			
                    			swap(pos, rightChild(pos)); 
    		                    minHeapify(rightChild(pos)); 

                    		}
                    		
                    		
                    	}
                    	
                    }
            		
            		
            		}
            		
            	}
            	
            	
            }
        } 
    
  
    // Function to insert a node into the heap with heapify
    public sNode insert(int element1,int element2) 
    { 
        if (size >= maxsize) { 
            return null; 
        } 
        sNode tempnode=new sNode(element1,element2);
        Heap[++size] = tempnode;
        int current = size; 
         
        while (Heap[current].getExecuted_time_key_MH() <= Heap[parent(current)].getExecuted_time_key_MH()&& current>1) { 
        	if(Heap[current].getExecuted_time_key_MH() == Heap[parent(current)].getExecuted_time_key_MH())
        	{
        		if(Heap[current].getBuildingNum_key_RBT()<Heap[parent(current)].getBuildingNum_key_RBT()) {
        			
                    swap(current, parent(current)); 
                   // current = parent(current); 
                }
        		
        		
        		
        	}
        	else {
            swap(current, parent(current)); 
           // current = parent(current);
        	}
        	current = parent(current);
        }
        
        return tempnode;
    }
    // Temperory Insert into Min Heap 
    public sNode tempinsert(int element1,int element2) {
    	
    	 if (size >= maxsize) { 
             return null; 
         } 
         sNode tempnode=new sNode(element1,element2);
         Heap[++size] = tempnode;
         
         return tempnode;
    	
    }
  
    // Function to print the contents of the heap 
    //For Testing Only
    public void print() 
    { 
        for (int i = 1; i <= size / 2; i++) { 
        	if(2*i+1>size) {
        		System.out.print(" PARENT : " + Heap[i].getBuildingNum_key_RBT()
                        + " LEFT CHILD : " + Heap[2 * i].getBuildingNum_key_RBT()); 
                System.out.println(); 

        	}
        	else
        	{
            System.out.print(" PARENT : " + Heap[i].getBuildingNum_key_RBT()
                             + " LEFT CHILD : " + Heap[2 * i].getBuildingNum_key_RBT()
                             + " RIGHT CHILD :" + Heap[2 * i + 1].getBuildingNum_key_RBT()); 
            System.out.println(); 
        	}
        } 
    } 
  
    // Function to build the min heap using 
    // the minHeapify 
    public void minHeap() 
    { 
        for (int pos = (size / 2); pos >= 1; pos--) { 
            minHeapify(pos); 
        } 
    } 
  
    // Function to remove and return the minimum 
    // element from the heap 
    public sNode remove() 
    { 
    	sNode popped = Heap[FRONT]; 
    	if(size>1) {
        Heap[FRONT] = Heap[size];
        Heap[size]=null;
        size--;
        minHeapify(FRONT);
    	}else {
    		
    		Heap[FRONT]=null;
    		size=0;
    	}
        return popped; 
    } 
    //For Testing Only
    public int printbuilding(int key) {
    	
    return 0;
    	
    }
  
    // For Testing Only 
    public static void main(String[] arg) 
    { 
        System.out.println("The Min Heap is "); 
        MinHeap minHeap = new MinHeap(15); 
        minHeap.insert(1,5); 
        minHeap.insert(3,17); 
        minHeap.insert(6,19); 
        minHeap.insert(2,3); 
       
        minHeap.insert(4,10); 
        minHeap.insert(5,84); 
      
        minHeap.insert(7,6); 
        minHeap.insert(8,22); 
        minHeap.insert(9,9); 
        minHeap.minHeap(); 
  
        minHeap.print(); 
        System.out.println("The Min val is " + minHeap.remove().getBuildingNum_key_RBT()); 
        minHeap.print();
        System.out.println("The Min val is " + minHeap.remove().getBuildingNum_key_RBT()); 
        minHeap.print();
        System.out.println("The Min val is " + minHeap.remove().getBuildingNum_key_RBT()); 
        minHeap.print();
    } 
} 