package scale;

import model.Automobile;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public class EditOptions extends Thread{
	private Automobile editAuto;// Auto used to edit
	private int funcNum=0;//which function users want to use
	private int threadID; // current thread ID	
	private String newOptionSetName;
	private String optionSetName;
	private String optionName;
	private float newPrice;

	/*
	 * constructor
	 * 
	 * constructor to initialize for updating option price
	 */
	public EditOptions(int threadID, int funcNum, Automobile editAuto, String optionSetName,String optionName, float newPrice) {
		this.editAuto = editAuto;
		this.optionSetName = optionSetName;
		this.threadID = threadID;
		this.optionName = optionName;
		this.newPrice= newPrice;
		this.funcNum=funcNum;		
	}

	/*
	 * constructor
	 * 
	 * constructor to initialize for updating option set name
	 */
	public EditOptions(int threadID, int funcNum, Automobile editAuto, String optionSetName,String newOptionSetName) {
		this.editAuto = editAuto;
		this.optionSetName = optionSetName;
		this.threadID = threadID;
		this.newOptionSetName = newOptionSetName;
		this.funcNum=funcNum;		
	}

	/*
	 * run()
	 * 
	 * choose which function to use
	 */
	public void run() {
		if(funcNum==1)
			threadUpdateSetName();
		else if(funcNum==2){
			try {
				threadUpdateOptionPrice();
			} catch (Exception e) {
				System.out.println("Error " + e.toString());
			}
		}
	}
	
	/*
	 * threadUpdateSetName()
	 * 
	 * using thread to update option set name
	 */
	public void threadUpdateSetName() {
		// array of option set name to test synchronization of 2 threads
		String[] ThreadOptionSetName = { optionSetName, "opSetName1", 
				"opSetName2", "opSetName3", "opSetName4","opSetName5",
				newOptionSetName,optionSetName };

		// delete/comment the line below to test desynchronization
		synchronized (editAuto) { 
			for (int i = 0; i < ThreadOptionSetName.length - 1; i++) {
				randomWait();
				try {
					//ThreadOptionSetName[i] is current name, ThreadOptionSetName[i+1] is new name
					editAuto.updateOptionSetName(ThreadOptionSetName[i],
							ThreadOptionSetName[i + 1]);
					System.out.println("Thread" + threadID + " : "
									+ "Previous Option Set Name: "
									+ ThreadOptionSetName[i] + "; Now name: "
									+ editAuto
											.getOptionSetName(ThreadOptionSetName[i + 1]));
				} catch (Exception e) {
					System.out.println("Thread" + threadID + " : "
							+ "Error  " + e.toString());
				}

			}
		}
	}

	/*
	 * threadUpdateOptionPrice()
	 * 
	 * using thread to update option price
	 */
	public void threadUpdateOptionPrice() throws Exception
	 {
		// delete/comment the line below to test desynchronization
	synchronized (editAuto) { 
	for (int i = 0; i < 5; i++) {
		randomWait();
		System.out.println("Thread " + threadID + " : " 
		+ optionSetName + " " + optionName + " Previous Price "
				+ editAuto.getOptionPrice(optionSetName, optionName));
		editAuto.updateOptionPrice(optionSetName, optionName,
				i + newPrice);
		System.out.println("Thread " + threadID + " : " 
				+ optionSetName + " " + optionName + " Price "
				+ editAuto.getOptionPrice(optionSetName, optionName));
//		editAuto.print();
	}
}
}

	// wait for a random time
	void randomWait() {
		try {
			Thread.currentThread().sleep((long) (3000 * Math.random()));
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
}
