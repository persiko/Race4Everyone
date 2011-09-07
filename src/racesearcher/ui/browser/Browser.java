package racesearcher.ui.browser;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Pär Sikö
 */
public final class Browser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = new WebEngine("http://www.epsilon.nu");

    public Browser(String page) {
       this();
       setPage(page);
    }
    
    public Browser() {
        browser.setEngine(webEngine);
        getChildren().add(browser);
        setId("exposeeComponent");
    }

    public void setPage(String page) {
        webEngine.load(page);
    }
    
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    @Override protected double computePrefWidth(double height) {
        return 600;
    }

    @Override protected double computePrefHeight(double width) {
        return 400;
    }
}
