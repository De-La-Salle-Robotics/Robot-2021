package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

public class HangerConfigs {
    public static void configHanger(VictorSPX winch, VictorSPX lift) {
        VictorSPXConfiguration winchConfigs = new VictorSPXConfiguration();

        winch.configAllSettings(winchConfigs);

        VictorSPXConfiguration liftConfigs = new VictorSPXConfiguration();

        lift.configAllSettings(liftConfigs);

        winch.setInverted(true); /* Winch is inverted */
    }
}