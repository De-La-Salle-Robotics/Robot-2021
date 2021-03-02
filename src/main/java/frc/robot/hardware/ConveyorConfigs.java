package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class ConveyorConfigs {
    public static void configConveyor(VictorSPX belt, TalonFX feeder) {
        VictorSPXConfiguration beltConfigs = new VictorSPXConfiguration();

        belt.configAllSettings(beltConfigs);
        belt.setInverted(true);

        TalonFXConfiguration feederConfigs = new TalonFXConfiguration();

        feeder.configAllSettings(feederConfigs);
        feeder.setInverted(true);
    }
}
