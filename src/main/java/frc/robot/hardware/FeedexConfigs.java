package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

public class FeedexConfigs {
    public static void configFeedex(VictorSPX spinner, TalonFX feeder) {
        VictorSPXConfiguration spinnerConfigs = new VictorSPXConfiguration();

        spinner.configAllSettings(spinnerConfigs);
        spinner.setInverted(true);

        TalonFXConfiguration feederConfigs = new TalonFXConfiguration();

        feederConfigs.supplyCurrLimit.triggerThresholdCurrent = 35;
        feederConfigs.supplyCurrLimit.triggerThresholdTime = 0.0;
        feederConfigs.supplyCurrLimit.currentLimit = 35;
        feederConfigs.supplyCurrLimit.enable = true;

        feeder.configAllSettings(feederConfigs);
        feeder.setInverted(false);
    }
}
