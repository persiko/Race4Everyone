/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package racesearcher;

import domain.Participant;
import domain.Race;
import domain.RaceImage;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import race.builder.RaceBuilder;
import racesearcher.ui.browser.Browser;
import racesearcher.ui.exposee.Expose;

import racesearcher.imagecache.ImageCache;
import racesearcher.ui.UIFactory;
import racesearcher.ui.map.MapView;
import racesearcher.ui.racelist.RaceList.RaceListCallback;

/**
 *
 * @author eitpso
 */
public class RaceSearcher extends Application {

    private ListView<Image> raceImageList;
    private Browser browser;
    private BorderPane map;
    private Expose expose;
    private TableView<Participant> topList;

    static { // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(RaceSearcher.class, args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Scene and container
        primaryStage.setTitle("Hello World");
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, UIFactory.getBackgroundGradient());

        // Set style sheet
        scene.getStylesheets().add(RaceSearcher.class.getResource("style.css").
                toExternalForm());

        // Get races
        final List<Race> raceData = RaceBuilder.getRaces();

        // Load images
        for (Race race : raceData) {
            ImageCache.loadImages(race.getImages());
            ImageCache.loadImages(race.getLogo());
        }

        BorderPane leftPane = new BorderPane();
        borderPane.setLeft(leftPane);

        // Create search dialog
        TextBox searchField = UIFactory.getSearchField();
        HBox searchPane = new HBox(5);
        HBox.setHgrow(searchField, Priority.ALWAYS);
        HBox.setMargin(searchField, new Insets(5, 10, 0, 10));
        searchPane.getChildren().addAll(searchField);
        leftPane.setTop(searchPane);


        // Create and populate race list
        ListView<Race> raceView = UIFactory.getRace(new RaceListCallbackImpl());
        raceView.setItems(FXCollections.observableArrayList(raceData));
        leftPane.setCenter(raceView);

        StackPane stackPane = new StackPane();

        // Media 
        raceImageList = UIFactory.getMediaList();
        raceImageList.setMaxHeight(300);
        stackPane.getChildren().add(raceImageList);
        StackPane.setAlignment(raceImageList, Pos.BOTTOM_CENTER);

        // pane for showing the expose mode in.
        StackPane exposePane = new StackPane();
        exposePane.setMaxHeight(600);

        StackPane.setAlignment(exposePane, Pos.TOP_CENTER);

        // Pie Chart
        PieChart chart = UIFactory.getPieChart();
        chart.setId("#exposeeComponent");
        exposePane.getChildren().add(chart);

        // Top list
        topList = UIFactory.getParticipantTable();
        topList.setId("#exposeeComponent");
        exposePane.getChildren().add(topList);


        // Web
        browser = UIFactory.getBrowser();
        browser.setId("#exposeeComponent");
        exposePane.getChildren().add(browser);

        // Map
        map = new MapView().getMapView();
        map.setPrefHeight(600);
        map.setId("#exposeeComponent");
        exposePane.getChildren().add(map);
        
        // Add stack pane to center
        stackPane.getChildren().add(exposePane);
        borderPane.setCenter(stackPane);
        raceImageList.toFront();

        // Expose
        expose = new Expose();
        List<Expose.State> states = new ArrayList<>();
        states.add(new Expose.State(browser, 0, 0, -200, -150,
                300, 200));
        states.add(new Expose.State(chart, 0, 0, 150, -150,
                360, 200));
        states.add(new Expose.State(topList, 0, 0, -200, 100,
                300, 200));
        states.add(new Expose.State(map, 0, 0, 150, 100,
                300, 200));
        expose.createExpose(states, 300);
        addExposeKeyListener(scene);

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setVisible(true);
    }

    private void addExposeKeyListener(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F4) {
                    expose.playExpose();
                }
            }
        });
    }

    private class RaceListCallbackImpl implements RaceListCallback {

        public RaceListCallbackImpl() {
        }

        @Override
        public void raceChanged(Race race) {

            browser.setPage(race.getWeb());
            List<RaceImage> images = race.getImages();
            List<Image> mediaList = new ArrayList<>();
            for (RaceImage raceImage : images) {
                mediaList.add(ImageCache.getImage(raceImage.getId()));
            }
            raceImageList.setItems(FXCollections.observableArrayList(mediaList));
            topList.setItems(FXCollections.observableArrayList(race.
                    getMaleParticipants()));
        }
    }
}
