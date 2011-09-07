package racesearcher.ui.mediaview;

import javafx.animation.Animation.Status;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 *
 * @author Pär Sikö
 */
public class PhotoView {

    public final ListView<Image> getPhotoView() {

        final ListView<Image> photoView = new ListView<>();
        // CSS reference
        photoView.setId("photoView");
        photoView.setOrientation(Orientation.HORIZONTAL);
        // Customize each individual item
        photoView.setCellFactory(new MediaCellFactory());
        return photoView;
    }

    /**
     * Cell factory customizes items in the list.
     * Our customization consists of adding an image, a frame and a drop shadow.
     */
    private class MediaCellFactory implements
            Callback<ListView<Image>, ListCell<Image>> {

        @Override
        public ListCell<Image> call(final ListView<Image> param) {
            // stack pane where items are stacked on top of each other.
            final StackPane stack = new StackPane();
            // the actual image
            final ImageView imageView = new ImageView();
            imageView.setId("photoImage");
            // the border around the image
            final Rectangle imageBorder = new Rectangle();
            imageBorder.setId("photoBorder");
            // Default is centered, we want both image and border to be located
            // in the bottom.            
            StackPane.setAlignment(imageBorder, Pos.BOTTOM_CENTER);
            StackPane.setAlignment(imageView, Pos.BOTTOM_CENTER);
            stack.getChildren().addAll(imageBorder, imageView);

            final ListCell<Image> cell = new ListCell<Image>() {

                @Override
                public void updateItem(Image item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        // For every new cell in the list we need to set a new image.
                        imageView.setImage(item);
                        // customize the border to be just big enough to enclose the image.
                        // since we can't be sure if a style sheet will change the stroke width
                        // in the future, we have to bind the width.
                        imageBorder.widthProperty().bind(item.widthProperty().
                                add(imageBorder.strokeWidthProperty()));
                        imageBorder.heightProperty().bind(item.heightProperty().
                                add(imageBorder.strokeWidthProperty()));
                        setNode(stack);
                    }
                }
            };
            cell.setId("photoCell");

            // Add animation behaviour to the cell.
            // When the mouse enters a cell we want it to scale to twice its size.
            // When mouse exits the cell should go back to normal.
            final int duration = 200;
            // Create and customize the scale transition
            final ScaleTransition scale =
                    new ScaleTransition(Duration.valueOf(duration),
                    cell);
            scale.setToX(2);
            scale.setToY(2);
            // Scaling the cell isn't enough to make it look good, we also need
            // to move the cell upwards to make the entire cell visible.
            final TranslateTransition translate =
                    new TranslateTransition(Duration.valueOf(duration), cell);
            translate.setToY(-55);
            // Do the two animations in parallel.
            final ParallelTransition parallel = new ParallelTransition(
                    scale, translate);

            
            cell.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    scale.setToX(2);
                    scale.setToY(2);
                    translate.setToY(-145);
                    cell.toFront();
                    parallel.playFromStart();
                }
            });

            cell.setOnMouseExited(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if (parallel.getStatus() == Status.RUNNING) {
                        parallel.stop();
                    }
                    scale.setToX(1);
                    scale.setToY(1);
                    translate.setToY(0);
                    cell.toBack();
                    parallel.playFromStart();
                }
            });

            return cell;
        }
    }
}
