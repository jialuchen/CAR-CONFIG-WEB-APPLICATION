package adapter;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public interface EditAuto {
	//used to update option price
	public void edit(int funcNum,String model, String optionSetName, String optionName, float newPrice);
	//used to update option set name
	public void edit(int funcNum,String model, String optionSetName, String newOptionSetName);
}
