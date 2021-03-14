package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.LEDChannel;

public class LED {
    /** Add some Constants for our colors */
    public final Color RED = new Color(140, 232, 21);

    public final Color GREEN = new Color(255, 111, 16);
    public final Color BLUE = new Color(0, 0, 1);

    public class Color {
        public Color(double red, double green, double blue) {
            redVal = red;
            greenVal = green;
            blueVal = blue;
        }

        double redVal;
        double greenVal;
        double blueVal;
    }

    private CANifier ledController;

    public LED(CANifier ledController) {
        this.ledController = ledController;
    }

    public void lighting(Color color) {
        ledController.setLEDOutput(color.redVal, LEDChannel.LEDChannelB);
        ledController.setLEDOutput(color.greenVal, LEDChannel.LEDChannelA);
        ledController.setLEDOutput(color.blueVal, LEDChannel.LEDChannelC);
    }
}
