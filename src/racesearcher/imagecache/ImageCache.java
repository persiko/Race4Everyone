package racesearcher.imagecache;

import domain.RaceImage;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;

/**
 *
 * @author Pär Sikö
 */
public class ImageCache {

    private static final Map<String, Image> cachedImages = new HashMap<>();

    public static Image getImage(String imageId) {
        return cachedImages.get(imageId);
    }

    public static void loadImages(RaceImage... images) {
        if (images != null) {
            for (RaceImage raceImage : images) {
                if (raceImage == null) {
                    continue;
                }
                try {
                    final URI uri = URI.create(raceImage.getSource());
                    Image image = new Image(uri.toURL().toExternalForm(), true);
                    cachedImages.put(raceImage.getId(), image);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void loadImages(List<RaceImage> images) {
        loadImages(images.toArray(new RaceImage[0]));
    }

    private ImageCache() {
    }
}
