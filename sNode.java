
import java.awt.Color;

public class sNode {
     //Building Number which is key value for Red black tree
	private int buildingNum_key_RBT;
	
	
	//Executed_time which is key value for Min Heap
	 private int executed_time_key_MH;
	
	//Total_time as input
	 private int total_time;
	
	//Parent of the node(Only applicable to RBT, null for Min  heap )
	 private sNode parent;
	
	//Left child of the node(Only applicable to RBT, null for Min  heap )
     private sNode left;
	
	//Right child of the node(Only applicable to RBT, null for Min  heap )
     private sNode right;
	
     //Color of the Node(Only applicable to RBT, null for Min  heap)
	 Color color = Color.black;
	 
	 public sNode() {
		 super();
		 }

	public sNode(int buildingNum_key_RBT, int total_time) {
		super();
		this.buildingNum_key_RBT = buildingNum_key_RBT;
		this.executed_time_key_MH = 0;
		this.total_time = total_time;
	}

	public int getBuildingNum_key_RBT() {
		return buildingNum_key_RBT;
	}

	public void setBuildingNum_key_RBT(int buildingNum_key_RBT) {
		this.buildingNum_key_RBT = buildingNum_key_RBT;
	}

	public int getExecuted_time_key_MH() {
		return executed_time_key_MH;
	}

	public void setExecuted_time_key_MH(int executed_time_key_MH) {
		this.executed_time_key_MH = executed_time_key_MH;
	}

	public int getTotal_time() {
		return total_time;
	}

	public void setTotal_time(int total_time) {
		this.total_time = total_time;
	}

	public sNode getParent() {
		return parent;
	}

	public void setParent(sNode parent) {
		this.parent = parent;
	}

	public sNode getLeft() {
		return left;
	}

	public void setLeft(sNode left) {
		this.left = left;
	}

	public sNode getRight() {
		return right;
	}

	public void setRight(sNode right) {
		this.right = right;
	}
}
