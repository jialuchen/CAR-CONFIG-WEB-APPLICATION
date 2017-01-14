package adapter;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public interface UpdateAuto {
	
	public void updateOptionSetName(String ModelName, String OptionSetName,
			String newName);

	public void updateOptionPrice(String ModelName, String Optionname,
			String Option, float newprice);
}
