package Application;
public class priceDate {						// declare string variables
	static String priceDate;
	static String opening_Price;
	static String closing_Price;
	

	priceDate() {
	}
	
	public priceDate(String d, String o, String c) {		// initialize string variables
		priceDate = d;
		opening_Price = o;
		closing_Price = c;
	}
	
	// return string variables
	public static String getDate() {
		return priceDate;
	}
	public static String getOpeningPrice() {
		return opening_Price;
	}
	public static String getClosingPrice() {
		return closing_Price;
	}
	

}