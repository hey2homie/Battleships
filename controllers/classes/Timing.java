package controllers.classes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

// TODO: Somehow move this to one of the Utilities methods

public class Timing {

    /*
    Self-explanatory I believe
     */

    int millis;
    Label timer;

    public Timing(int millis) {
        this.millis = millis;
    }

    public void setTimer(Label timer) {
        this.timer = timer;
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> CountDown()));

    public void CountDown() {
        if (millis != 0) {
            millis -= 1000;
            if (Utilities.turn2) {
                Utilities.timePlayer2 += 1;
            } else {
                Utilities.timePlayer1 += 1;
            }
        }
        timer.setText(String.valueOf(millis / 1000));
        if (millis == 0) {
            Utilities.runOutOfTime();
            timeline.stop();
        }
    }

    public void runTimer() {
        timeline.setCycleCount(millis);
        timeline.playFromStart();
    }

    public void stop() {
        timeline.stop();
    }
}
