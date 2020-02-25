package frc.robot.hardware;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

public class HangerConfigs {
    public static void configHanger(VictorSPX winch, VictorSPX lift, CANifier can) {
        VictorSPXConfiguration winchConfigs = new VictorSPXConfiguration();

        winch.configAllSettings(winchConfigs);

        VictorSPXConfiguration liftConfigs = new VictorSPXConfiguration();
        liftConfigs.forwardLimitSwitchDeviceID = can.getDeviceID();
        liftConfigs.forwardLimitSwitchSource = RemoteLimitSwitchSource.RemoteCANifier;

        lift.configAllSettings(liftConfigs);

        winch.setInverted(true); /* Winch is inverted */
    }
}