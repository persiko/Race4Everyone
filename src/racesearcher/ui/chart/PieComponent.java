package racesearcher.ui.chart;

import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;
import javafx.scene.effect.DropShadow;

/**
 *
 * @author Pär Sikö
 */
public class PieComponent {
    
    public PieChart getPieChart() {
        
        PieChart chart = new PieChart();

        chart.setData(FXCollections.observableArrayList(
                new PieChart.Data("< 20", 0.1),
                new PieChart.Data("20 - 30", 0.30),
                new PieChart.Data("31 - 40", 0.40),
                new PieChart.Data("41 - 50", 0.15),
                new PieChart.Data("> 51", 0.05)
                ));
        
//        DropShadow dropShadow = new DropShadow();
//        dropShadow.setOffsetX(12);
//        dropShadow.setOffsetY(12);
//        dropShadow.setRadius(20);
//        chart.setEffect(dropShadow); 
        
        return chart;
    }
    
}
