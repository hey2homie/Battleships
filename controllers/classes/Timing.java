package controllers.classes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timing {
    
    long millis; 
    Label timer;

    public Timing(long millis) {
        this.millis = millis;
    }

    public void setTimer(Label timer) {
        this.timer = timer;
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> CountDown()));

    public void CountDown() {
        if (millis == 0) {
            millis = 0;
        } else {
            millis -= 1000;
            if (Utilities.turn2) {
                Utilities.timePlayer2 += 1;
            } else {
                Utilities.timePlayer1 += 1;
            }
        }
        timer.setText(String.valueOf(millis / 1000));
    }

    public void runTimer() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
    }

    public void stop() {
        timeline.stop();
    }
}
