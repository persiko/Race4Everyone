package racesearcher.ui.racelist;

import domain.Race;
import domain.Sport;
import java.util.Map;
import java.util.Map.Entry;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 *
 * @author Pär Sikö
 */
public class RaceList {

    public ListView<Race> getRaceList(final RaceListCallback callback) {

        final ListView<Race> raceView = new ListView<>();
        ObservableList<Race> raceList = FXCollections.observableArrayList(
                new Race(), new Race(), new Race());
        raceView.setItems(raceList);
        raceView.setCellFactory(new Callback<ListView<Race>, ListCell<Race>>() {

            @Override
            public ListCell<Race> call(ListView<Race> param) {
                // Container
                final GridPane gridPane = new GridPane();
                gridPane.setVgap(10);
                gridPane.setId("list-cell-container");

                // Race name
                final Label name = new Label();
                name.setId("race-list-name");

                // Sport image/distance container
                final HBox sportBox = new HBox();
                sportBox.setTranslateX(5);

                // Sport images
                final ImageView run = new ImageView(new Image(getClass().
                        getResourceAsStream("run.png")));
                run.setBlendMode(BlendMode.MULTIPLY);
                run.setId("race-list-distance");

                final ImageView bike = new ImageView(new Image(getClass().
                        getResourceAsStream("bike.png")));
                bike.setBlendMode(BlendMode.MULTIPLY);
                bike.setId("race-list-distance");

                final ImageView swim = new ImageView(new Image(getClass().
                        getResourceAsStream("swim.png")));
                swim.setBlendMode(BlendMode.MULTIPLY);
                swim.setId("race-list-distance");

                // Sport distances
                final Label runDistance = new Label();
                runDistance.setId("race-list-distance");
                runDistance.setPrefWidth(30);

                final Label swimDistance = new Label();
                swimDistance.setId("race-list-distance");
                swimDistance.setPrefWidth(30);

                final Label bikeDistance = new Label();
                bikeDistance.setId("race-list-distance");
                bikeDistance.setPrefWidth(30);

                sportBox.getChildren().addAll(swim, swimDistance, bike,
                        bikeDistance, run, runDistance);

                gridPane.add(name, 0, 0);
                gridPane.add(sportBox, 0, 1);
                final ListCell<Race> cell = new ListCell<Race>() {

                    @Override
                    public void updateItem(Race item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {

                            final Map<Sport, String> sportAndDistance =
                                    item.getSportAndDistance();
                            if (!sportAndDistance.containsKey(
                                    Sport.SWIM)) {
                                sportBox.getChildren().remove(swim);
                                sportBox.getChildren().remove(swimDistance);
                            } else {
                                swimDistance.setText(sportAndDistance.get(Sport.SWIM));
                            }
                            if (!sportAndDistance.containsKey(
                                    Sport.BIKE)) {
                                sportBox.getChildren().remove(bike);
                                sportBox.getChildren().remove(bikeDistance);
                            }
                            if (!sportAndDistance.containsKey(
                                    Sport.RUN)) {
                                sportBox.getChildren().remove(run);
                                sportBox.getChildren().remove(runDistance);
                            }

                            name.setText(item.getName());

                            setNode(gridPane);
                            setPrefWidth(200);
                            setPrefHeight(65);
                        }
                    }
                };
                cell.setId("race-list-cell");
                return cell;

            }
        });


        raceView.setStyle(
                "-fx-background-color: rgba(0,0,0,0);");
        raceView.setPrefWidth(250);
        ObjectProperty<Race> op = raceView.getFocusModel().focusedItemProperty();
        op.addListener(new ChangeListener<Race>() {

            public void changed(ObservableValue<? extends Race> observable,
                    Race oldValue, Race newValue) {
                callback.raceChanged(observable.getValue());
            }
        });
        return raceView;
    }

    public static interface RaceListCallback {

        void raceChanged(Race race);
    }
}
