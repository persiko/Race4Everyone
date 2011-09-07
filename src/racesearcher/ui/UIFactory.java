package racesearcher.ui;

import domain.Participant;
import domain.Race;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import racesearcher.ui.browser.Browser;
import racesearcher.ui.chart.PieComponent;
import racesearcher.ui.mediaview.PhotoView;
import racesearcher.ui.racelist.RaceList;
import racesearcher.ui.toplist.TopList;

/**
 *
 * @author Pär Sikö
 */
public class UIFactory {

    public static ListView<Race> getRace(RaceList.RaceListCallback callback) {
        return new RaceList().getRaceList(callback);
    }
    
    public static ListView<Image> getMediaList() {
        return new PhotoView().getPhotoView(); 
    }

    public static Paint getBackgroundGradient() {
        return new RadialGradient(
                0, // focus angle
                0.4, // focus distance
                1, // centerX
                0, // centerY
                0.9, // radius
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0f, Color.web("#999999")),
                new Stop(1.1f, Color.web("#040404")));
    }

    public static PieChart getPieChart() {
        return new PieComponent().getPieChart();
    }

    public static TableView<Participant> getParticipantTable() {
        return new TopList().getTopList();
    }

    public static TextBox getSearchField() {
        final TextBox textBox = new TextBox(20);
        textBox.setText("Search");
        textBox.setStyle("-fx-text-fill: #BBBBBB");
        textBox.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                textBox.setStyle("-fx-text-fill: black");
                textBox.setText("");
            }
        });
        return textBox;
    }
    
    public static Label getLabel() {
        return new Label();
    }

    public static Browser getBrowser() {
        return new Browser();
    }

    public static Browser getBrowser(String page) {
        return new Browser(page);
    }

    private UIFactory() {
    }
    
}
