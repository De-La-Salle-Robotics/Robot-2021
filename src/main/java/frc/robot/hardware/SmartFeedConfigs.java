package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class SmartFeedConfigs {
    public static void configSmartFeed(TalonFX feeder, TalonFX flywheel) {
        TalonFXConfiguration feederConfigs = new TalonFXConfiguration();

        feeder.configAllSettings(feederConfigs);
        feeder.setInverted(true);

        TalonFXConfiguration flywheelConfigs = new TalonFXConfiguration();

        flywheel.configAllSettings(flywheelConfigs);
    }
}
