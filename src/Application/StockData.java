package Application;
import java.util.Arrays;
import java.util.Scanner;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class StockData extends Application{
	
	
	
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
		Stage window;
		Scene scene1, scene2;

		
		@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
		public void start(Stage primaryStage) throws Exception {
			window = primaryStage;
			
			primaryStage.setTitle("Stock Market Fundamental Analysis");
			GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));

	        Text scene1Title = new Text("Stock Market Fundamental Analysis");
	        scene1Title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
	        grid.add(scene1Title, 0, 0, 2, 1);

	        Label tickerNum = new Label("Enter Ticker Number:");
	        grid.add(tickerNum, 0, 1);

	        TextField userTextField = new TextField();
	        grid.add(userTextField, 1, 1);

	        Button btn1 = new Button("Go");
	        HBox hbBtn1 = new HBox(10);
	        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
	        hbBtn1.getChildren().add(btn1);
	        grid.add(hbBtn1, 1, 4);
	        btn1.setOnAction(e -> window.setScene(scene2));
	        
	        /*Scanner reader = new Scanner (System.in);
			System.out.print("What is the ticket number? ");
			String s = reader.next();
			reader.close();
			priceDate[] variable = new priceDate[3];
			System.arraycopy( priceDateArray(s), 0, variable, 0, priceDateArray(s).length );*/
			
	        
	        window.setTitle("AAPL pricing 2019 March");					//defining the axes
	        final CategoryAxis xAxis = new CategoryAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        xAxis.setLabel("Date");
	        yAxis.setLabel("Price");
	        
	        final LineChart<String,Number> chart = 						//creating the chart
	                new LineChart<String,Number>(xAxis,yAxis);
	                
	        chart.setTitle("Stock Monitoring, past 5 years");					//defining a series
	        XYChart.Series opening = new XYChart.Series();
	        XYChart.Series closing = new XYChart.Series();
	        opening.setName("Opening Prices"); 	
	        closing.setName("Closing Prices");//populating the series with data
	        
	        opening.getData().add(new XYChart.Data("A", 15));
	        closing.getData().add(new XYChart.Data("A", 12));	
	        opening.getData().add(new XYChart.Data("B", 12));
	        closing.getData().add(new XYChart.Data("B", 19));	
	        opening.getData().add(new XYChart.Data("C", 17));
	        closing.getData().add(new XYChart.Data("C", 38));
	        opening.getData().add(new XYChart.Data("D", 34));
	        closing.getData().add(new XYChart.Data("D", 49));	
	        opening.getData().add(new XYChart.Data("E", 50));
	        closing.getData().add(new XYChart.Data("E", 59));	
	        
	        scene2 = new Scene(chart, 1000, 750);
	        chart.getData().add(opening);
	        chart.getData().add(closing);
	        
	       /* Button btn2 = new Button("Go Back");
	        HBox hbBtn2 = new HBox(10);
	        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
	        hbBtn2.getChildren().add(btn2);
	        chart.add(hbBtn2, 10, 10);
	        btn2.setOnAction(e -> window.setScene(scene1));
	        
	        */
	        
	   
	        Scene scene1 = new Scene(grid, 800, 600);
	        window.setScene(scene1);
	        window.show();
	    }
	
	public static priceDate[] priceDateArray(String s) {
		priceDate[] testing = new priceDate[2];
		priceDate first = new priceDate("4/20/1900","1500","1700");
		priceDate second = new priceDate("4/21/1900","1700","1876");
		testing[0] = first;
		testing[1] = second;
		
		String url_fundamentals = "https://cloud.iexapis.com/beta/stock/" + s.toLowerCase() + "/quote?token=sk_f9beaa938be041ce97f312d956112fd3";
		String url_Historic_data= "https://cloud.iexapis.com/beta/stock/" + s.toLowerCase() + "/chart/5y?token=sk_f9beaa938be041ce97f312d956112fd3";
		//priceDate[] Historic_data = createPriceDate(Historic_Data(url_Historic_data));
		return testing;
		
	}
		
		
	public static void main (String[] args) {
		launch(args);
	}

	



		
	
}	


