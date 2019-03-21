import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


 
 
public class chartGUI extends Application {
 
    @Override public void start(Stage stage) {
        stage.setTitle("AAPL pricing 2019 March");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> chart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        chart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        int rand1;

        for(int i = 1; i<= 12; i++) {
        	rand1 = (int)(Math.random() * 226);
        	series.getData().add(new XYChart.Data(i, rand1));
        }
        
        Scene scene  = new Scene(chart,800,600);
        chart.getData().add(series);
       
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}