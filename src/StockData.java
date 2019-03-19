import java.util.Scanner;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import java.util.Arrays;

public class StockData {
	public static void main (String[] args) {
		Scanner reader = new Scanner (System.in);
		System.out.print("What is the ticket number? ");
		String s = reader.next();
		String url_fundamentals = "https://cloud.iexapis.com/beta/stock/" + s.toLowerCase() + "/quote?token=sk_003b77c361284dda94b15b42a8bfb667";
		String url_Historic_data= "https://cloud.iexapis.com/beta/stock/" + s.toLowerCase() + "/chart/5y?token=sk_003b77c361284dda94b15b42a8bfb667";
		//createCompanyObject(Company_Fundamentals(url_fundamentals));
		System.out.println(createPriceDate(Historic_Data(url_Historic_data))[1257].getDate());
		System.out.println(createPriceDate(Historic_Data(url_Historic_data))[1257].getOpeningPrice());
		System.out.println(createPriceDate(Historic_Data(url_Historic_data))[1257].getClosingPrice());
		System.out.println(createPriceDate(Historic_Data(url_Historic_data))[0].getDate());
		System.out.println(createPriceDate(Historic_Data(url_Historic_data))[0].getOpeningPrice());
		System.out.println(createPriceDate(Historic_Data(url_Historic_data))[0].getClosingPrice());
		
		
		/*
		String t = Company_Fundamentals(url_fundamentals)[0].substring(Company_Fundamentals(url_fundamentals)[0].indexOf(':')+2,Company_Fundamentals(url_fundamentals)[0].length()-1);
		String n =Company_Fundamentals(url_fundamentals)[1].substring(Company_Fundamentals(url_fundamentals)[1].indexOf(':')+2) + Company_Fundamentals(url_fundamentals)[2].substring(0,Company_Fundamentals(url_fundamentals)[2].length()-1);
		Company company_Data = new Company(t,n);
		System.out.println(Arrays.toString(Company_Fundamentals(url_fundamentals)));
		*/
		//System.out.println(company_Data.getName());
		//System.out.println(company_Data.getTicker());
		
	}


	
	
	
	
	
	// Returns String array with Company's name, symbol, current price, peRatio, and market Cap
	
	public static String[] Company_Fundamentals(String url) {
		try {
			final Document Data = Jsoup.connect(url).ignoreContentType(true).get();
			String all_Data = Data.outerHtml();
			all_Data = all_Data.substring(all_Data.indexOf('{') +1,all_Data.lastIndexOf('}'));
			String company_name = all_Data.substring(all_Data.indexOf("companyName\"")+14,all_Data.indexOf("\",\"calculationPrice"));
			String symbol = all_Data.substring(all_Data.indexOf(':')+2,all_Data.indexOf(",")-1);
			String peRatio = all_Data.substring(all_Data.indexOf("peRatio")+9,all_Data.indexOf(",\"week")); 
			String marketCap = all_Data.substring(all_Data.indexOf("marketCap")+11,all_Data.indexOf(",\"peRatio"));
			String latestPrice = all_Data.substring(all_Data.indexOf("latestPrice")+13,all_Data.indexOf(",\"latestSource"));
			String [] company_data = {company_name,symbol,peRatio,marketCap,latestPrice};
			return company_data;		
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String[] Historic_Data(String url) {
		try {
			final Document Data = Jsoup.connect(url).ignoreContentType(true).get();
			String all_Data = Data.outerHtml();
			all_Data = all_Data.substring(all_Data.indexOf('[') +1,all_Data.lastIndexOf(']'));
			String [] company_data = all_Data.split("},");
			String [] company_data_time = new String[company_data.length];
			for( int i = 0; i < company_data.length ; i ++ ) {
				String holder = (company_data[i]).substring((company_data[i]).indexOf("date"),(company_data[i]).indexOf("high"));
				holder = holder.substring(0,holder.indexOf('"')) + holder.substring(holder.indexOf('"')+1,holder.length()-2);
				company_data_time[i] = holder;
			}
			return company_data_time;
				
		}
		
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	// Creates company object from the indexes of a 5 index String array
	public static Company createCompanyObject(String[] input) {
		Company companyData = new Company(input[0],input[1],input[2],input[3],input[4]);
		System.out.println(companyData.getName());
		return companyData;
	
	}
	
	public static priceDate[] createPriceDate(String[] input) {
		try {
			priceDate[] dates = new priceDate[1258];
			int j = 0;
			for (String s: input) {
				String date = s.substring(s.indexOf("date")+6,s.indexOf("open")-3);
				String opening = s.substring(s.indexOf("open")+6,s.indexOf(",\"close"));
				String closing = s.substring(s.indexOf("close")+7);
				dates[j] =  new priceDate(date,opening,closing);
				j++;
			}
			return dates;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			
	}
	
	
	
}	


