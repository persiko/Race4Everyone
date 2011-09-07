package racesearcher.ui.exposee;

import java.util.List;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Creates an exposé like animation based on a number of States. When in expose
 * mode all components are minimized, as described in the components state. 
 * When the user clicks a component (in exposé mode) the component becomes
 * maximized, taking up as much space as possible, hiding the other exposé
 * components.
 * 
 * To create an exposé do the following:
 * <code>
 *      expose = new Expose();
 *      List<Expose.State> states = new ArrayList<>();
 *      states.add(new Expose.State(yourComponent, x, y, toXLocation, toYLocation, toWidth, toHeight));
 *      // add a state for every component.
 *      expose.createExpose(states, 300);
 *      // and then invoke play to start.
 *      expose.play();
 * </code>
 * 
 * @author eitpso
 */
public class Expose {

    private List<State> states;
    private Node frontNode;
    private boolean isInExposeMode = false;
    private long duration;

    /**
     * Creates an expose control/animation based on provided states.
     * Every state describes the exposé look of a single component, including
     * miniature width and height.
     * 
     * @param exposeStates component states, descibing the dimensions of the
     *        exposé components
     * @param duration duration of the exposé animation.
     */
    public final void createExpose(final List<State> exposeStates,
            final long duration) {
        this.states = exposeStates;
        this.duration = duration;
        for (final State state : states) {
            state.node.setOnMouseClicked(new EventHandler<MouseEvent>() {

                public void handle(MouseEvent event) {
                    if (isInExposeMode) {
                        frontNode = state.node;
                        createBackAnimations(states,
                                duration).play();
                        isInExposeMode = false;
                    }
                }
            });
        }
    }

    /**
     * Plays the exposé animation, putting related components in exposé mode.
     */
    public void playExpose() {
        createAnimations(states, duration).play();
        isInExposeMode = true;
    }

    /**
     * Describes a component when in exposé mode.
     */
    public static final class State {

        public Node node;
        public int startX;
        public int startY;
        public int endX;
        public int endY;
        public double endWidth;
        public double endHeight;

        public State(Node node, int startX, int startY, int endX, int endY,
                double endWidth,
                double endHeight) {
            this.node = node;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.endWidth = endWidth;
            this.endHeight = endHeight;
        }
    }

    private ParallelTransition createAnimations(final List<State> states,
            long duration) {
        final ParallelTransition exposeTransition = new ParallelTransition();

        for (State state : states) {
            TranslateTransition translateAnimation =
                    new TranslateTransition(Duration.valueOf(duration),
                    state.node);
            translateAnimation.setToX(state.endX);
            translateAnimation.setToY(state.endY);
            exposeTransition.getChildren().add(translateAnimation);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.
                    valueOf(duration),
                    state.node);
            scaleTransition.setToX(state.endWidth / state.node.getLayoutBounds().
                    getWidth());
            scaleTransition.setToY(state.endHeight
                    / state.node.getLayoutBounds().getHeight());
            exposeTransition.getChildren().add(scaleTransition);

            FadeTransition fadeTransition = new FadeTransition(Duration.valueOf(
                    duration),
                    state.node);
            fadeTransition.setToValue(1);
            exposeTransition.getChildren().add(fadeTransition);
        }

        return exposeTransition;
    }

    private ParallelTransition createBackAnimations(List<State> exposeStates,
            long duration) {
        ParallelTransition normalTransition = new ParallelTransition();

        for (State state : exposeStates) {
            TranslateTransition translateAnimation2 =
                    new TranslateTransition(Duration.valueOf(duration),
                    state.node);
            translateAnimation2.setToX(state.startX);
            translateAnimation2.setToY(state.startY);
            normalTransition.getChildren().add(translateAnimation2);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.
                    valueOf(duration),
                    state.node);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            normalTransition.getChildren().add(scaleTransition);

            if (state.node != frontNode) {

                FadeTransition fadeTransition = new FadeTransition(Duration.
                        valueOf(duration / 2),
                        state.node);
                fadeTransition.setToValue(0);
                normalTransition.getChildren().add(fadeTransition);
            }
        }

        return normalTransition;
    }
}
