package racesearcher.ui.toplist;

import domain.Participant;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;

/**
 *
 * @author Pär Sikö
 */
public class TopList {

    private ObservableList<Participant> items = FXCollections.observableArrayList();
    
    public TableView<Participant> getTopList() {
        TableView<Participant> table = new TableView<>();
        table.setItems(items);
        
        TableColumn<String> firstName = new TableColumn<>("First Name");
        firstName.setProperty("firstName");
        TableColumn<String> lastName = new TableColumn<>("Last Name");
        lastName.setProperty("lastName");
        TableColumn<String> time = new TableColumn<>("Result");
        time.setProperty("result");
        table.getColumns().addAll(firstName, lastName, time);

//        DropShadow dropShadow = new DropShadow();
//        dropShadow.setOffsetX(12);
//        dropShadow.setOffsetY(12);
//        dropShadow.setRadius(20);

//        table.setEffect(dropShadow);

        return table;

    }

    public void setItems(List<Participant> participants) {
        items.setAll(participants);
    }

}
