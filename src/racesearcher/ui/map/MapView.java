package racesearcher.ui.map;

import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 */
public class MapView {

    
    public BorderPane getMapView() {

        // create web engine and view
        final WebEngine webEngine = new WebEngine(getClass().getResource("/racesearcher/ui/map/map.html").toString());
        final WebView webView = new WebView(webEngine);
        // create map type buttons
        final ToggleGroup mapTypeGroup = new ToggleGroup();
        final ToggleButton road = new ToggleButton("Road");
        road.setSelected(true);
        road.setToggleGroup(mapTypeGroup);
        final ToggleButton satellite = new ToggleButton("Satellite");
        satellite.setToggleGroup(mapTypeGroup);
        final ToggleButton hybrid = new ToggleButton("Hybrid");
        hybrid.setToggleGroup(mapTypeGroup);
        final ToggleButton terrain = new ToggleButton("Terrain");
        terrain.setToggleGroup(mapTypeGroup);
        mapTypeGroup.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {

                    public void changed(
                            ObservableValue<? extends Toggle> observableValue,
                            Toggle toggle, Toggle toggle1) {
                        if (road.isSelected()) {
                            webEngine.executeScript("document.setMapTypeRoad()");
                        } else if (satellite.isSelected()) {
                            webEngine.executeScript(
                                    "document.setMapTypeSatellite()");
                        } else if (hybrid.isSelected()) {
                            webEngine.executeScript(
                                    "document.setMapTypeHybrid()");
                        } else if (terrain.isSelected()) {
                            webEngine.executeScript(
                                    "document.setMapTypeTerrain()");
                        }
                    }
                });

        Button zoomIn = new Button("Zoom In");
        zoomIn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                webEngine.executeScript("document.zoomIn()");
            }
        });
        Button zoomOut = new Button("Zoom Out");
        zoomOut.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                webEngine.executeScript("document.zoomOut()");
            }
        });
        // create toolbar
        ToolBar toolBar = new ToolBar();
        toolBar.getStyleClass().add("map-toolbar");
        toolBar.getItems().addAll(
                road, satellite, hybrid, terrain,
                createSpacer(),
                new Label("Location:"), zoomIn, zoomOut);
        // create root
        BorderPane root = new BorderPane();
        root.getStyleClass().add("map");
        root.setCenter(webView);
        root.setTop(toolBar);

        return root;
    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
}
