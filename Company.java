package Application;

public class Company {
	/*
	* declare String variables
	*/
	static String name;
	static String ticker_Number;
	static String peRatio;
	static String marketCap;
	static String latestPrice;
	
	// Base object instance
	Company () {
	}
	// Create instance of object class with name, tickernumber, peratio, marketcap, and latestprice
	Company(String n, String t, String pe, String m, String lp) {	
		name = n;
		ticker_Number = t;
		peRatio = pe;
		marketCap = m;
		latestPrice = lp;
	}
	
	public static String getName() {				// method to return name 
		return name;
	}
	
	public static String getTicker() {				// method to return ticker number
		return ticker_Number;
	}
	
	public static String getpeRatio() {				// method to return p/e ratio
		return peRatio;
	}
	
	public static String marketCap() {				// method to return market cap
		return marketCap;
	}

}
