package exception;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public enum EnumException {
	//5 errors
	FileNotFound(1), FileBasePriceNotFound(2), OptionSetNotFound(3), OptionNotFound(4), FileOptionPriceNotFound(5);
	
	private int value;
	//constructor
	private EnumException(int value) {
		this.value=value;
	}
	// error num getter
	public int getValue() {
		return value;
	}
	//error num setter
	private void setValue(int value) {
		this.value = value;
	}
}
