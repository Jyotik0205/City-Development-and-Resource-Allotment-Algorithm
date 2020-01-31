
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author jyotik.parikshya
 *
 */
public class risingCity {

	/**
	 * Global Time
	 */
	public static int gtime = 0;
	/**
	 * Red Black Tree Data Structure
	 */
	public static RedBlackTree rbt;
	/**
	 * Min Heap Data Structure
	 */
	public static MinHeap mh;
    /**
     * Flag for checking if processing is ON(1) or OFF(0)
     */
	public static int isprocessing = 0;
    /**
     * Local time, resets everytime after reching 5
     */
	public static int counter = 0;
	/**
	 * Flag for cchecking if any of the current buildings has been completed
	 */
	
	public static int issaturated=0;

	/**
	 * Constructor for initializing Red Black Tree and Min Heap
	 */
	public risingCity() {
		super();
		rbt = new RedBlackTree();
		mh = new MinHeap(2000);
	}

	/**
	 * @param element1 --> Building Number
	 * @param element2 --> Total Time
	 * 
	 *                 Inserts the node into MinHeap and Red Black Tree
	 */
	public static void insert(int element1, int element2) {

		sNode s = mh.insert(element1, element2);
		rbt.add(s);
	}

	/**
	 * Removes the minimum execution time element from both MinHeap and Red Black
	 * Tree
	 */
	public static sNode extractmin() {

		sNode n = mh.remove();

		return n;
	}

	/**
	 * Executes the task of picking up the minimum execution time element and taking
	 * action accordingly
	 */
	public static void pickdailybuilding() {
		if(mh.getSize()>0) {
			sNode node = mh.returnmin();

			node.setExecuted_time_key_MH(node.getExecuted_time_key_MH() + 1);
			isprocessing = 1;
			counter++;
		}


	}
	/*
	 * Removes the Min Node and Prints
	 */
	public static void removeandprint() {
		sNode node = extractmin();
		System.out.println("(" + node.getBuildingNum_key_RBT() + "," +gtime+ ")");
        rbt.remove(node); 
		mh.minHeap();  //Heapify
		issaturated=0; //Reset 
	}
   /*
    * Updates the building currently under processing
    */
	public static void updateprocessing() {

		if(mh.getSize()>0) {
              //if saturated
			if(mh.returnmin().getTotal_time() == mh.returnmin().getExecuted_time_key_MH())
			{
				isprocessing = 0;
				counter = 0;
				issaturated=1;
			}
			else
			{ 
				counter++;
				// Case when counter is still less than 5 
				if (counter <5) {
					mh.returnmin().setExecuted_time_key_MH(mh.returnmin().getExecuted_time_key_MH() + 1);
					if(mh.returnmin().getExecuted_time_key_MH()==mh.returnmin().getTotal_time()) {
						
						isprocessing = 0;
						counter = 0;
						issaturated=1;
						
					}

				} else {
					mh.returnmin().setExecuted_time_key_MH(mh.returnmin().getExecuted_time_key_MH() + 1);
					isprocessing = 0;
					counter = 0;
					//check for saturation
					if(mh.returnmin().getExecuted_time_key_MH()==mh.returnmin().getTotal_time()) {
						issaturated=1;
					}
					// if saturated than heapify after print
					if(issaturated!=1) {
					mh.minHeap(); //Min Heapify after processing finished
					}
				} 

			}
		}

	}

	/**
	 * @param arg
	 * @throws IOException
	 * 
	 *                     Main Function for all operations
	 */
	public static void main(String[] arg) throws IOException {

		File file = new File(arg[0]); //FIle Input
		PrintStream fileOut = new PrintStream("./output_file.txt"); //Set Output
		System.setOut(fileOut);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		risingCity w = new risingCity(); //Initializing for constructor;
		String st;

		while ((st = br.readLine()) != null ) { // for each input run a loop


			String[] s = st.split(":"); // Split to know the input day
			int a = Integer.parseInt(s[0]); //Current Day input from File
			if (a == gtime) {   //If Current day is equal to globl time 
				if (isprocessing == 1) {
					updateprocessing();
				} else {
					if(gtime!=0) //No operation on day 0
					{
						pickdailybuilding();
					
					}
				}
				String[] s1 = s[1].split("\\(");
				if (s1[0].equals(" Insert")) { // Insert Operation
					if(isprocessing==1)
					{
						//Extract the integers to be inserted
						String[] s2 = s1[1].split(",");
						int a1 = Integer.parseInt(s2[0]);
						String[] s3 = s2[1].split("\\)");
						int a2 = Integer.parseInt(s3[0]);
						//Insert without heapify
						sNode n1 = mh.tempinsert(a1, a2);
						rbt.add(n1);
						//insert(a1, a2);
					}
					else {
						String[] s2 = s1[1].split(",");
						int a1 = Integer.parseInt(s2[0]);
						String[] s3 = s2[1].split("\\)");
						int a2 = Integer.parseInt(s3[0]);
                        // Insert with Heapify
						insert(a1, a2);


					}
				}
				if (s1[0].equals(" PrintBuilding")) // Print Operation
				{
					String[] s2 = s1[1].split(",");
					if (s2.length == 1) {
						String[] s3 = s2[0].split("\\)");

						int a1 = Integer.parseInt(s3[0]);

						rbt.print(a1);
					} else {
						int a1 = Integer.parseInt(s2[0]);
						String[] s3 = s2[1].split("\\)");
						int a2 = Integer.parseInt(s3[0]);
						rbt.print(a1, a2);

					}

				}

				gtime++;  //Increment the Global Time

			} else {

                 // When a is not equal to gtime increment gtime till it reaches a
				while (gtime < a) {
					if(mh.getSize()>0) {
                       //Update when processing
						if (isprocessing == 1) {
							updateprocessing();
							if(issaturated==1) {
								
								removeandprint();
							}
						} else  // else pick a building
						{
							pickdailybuilding();
						}


					}
					gtime++;
				}
                // When a=gtime run the same thing as above
				if(isprocessing==1) {
					updateprocessing();
                    

				}
				else {
					pickdailybuilding();
				}
				String[] s1 = s[1].split("\\(");
				if (s1[0].equals(" Insert")) {
					 if(issaturated==1) {
							
							removeandprint();
						}
					if (isprocessing == 0) {

						String[] s2 = s1[1].split(",");
						int a1 = Integer.parseInt(s2[0]);
						String[] s3 = s2[1].split("\\)");
						int a2 = Integer.parseInt(s3[0]);
						sNode n = mh.insert(a1, a2);
						rbt.add(n);


					} else {

						String[] s2 = s1[1].split(",");
						int a1 = Integer.parseInt(s2[0]);
						String[] s3 = s2[1].split("\\)");
						int a2 = Integer.parseInt(s3[0]);
						sNode n1 = mh.tempinsert(a1, a2);
						rbt.add(n1);

					}
                     
				}
				if (s1[0].equals(" PrintBuilding")) // Print Operation
				{
					
					String[] s2 = s1[1].split(",");
					if (s2.length == 1) {
						String[] s3 = s2[0].split("\\)");

						int a1 = Integer.parseInt(s3[0]);

						rbt.print(a1);
					} else {
						int a1 = Integer.parseInt(s2[0]);
						String[] s3 = s2[1].split("\\)");
						int a2 = Integer.parseInt(s3[0]);
						rbt.print(a1, a2);

					}
                 if(issaturated==1) {
						
						removeandprint();
					}
				}
				gtime++;


			}

		}
        // After the File is finished
		while(mh.getSize()!=0) {

			if (isprocessing == 1) {
				updateprocessing();
				if(issaturated==1) {
					
					removeandprint();
				}
				gtime++;
			} else {
				mh.minHeap();
				pickdailybuilding();
				gtime++;
			}


		}

	}
}
