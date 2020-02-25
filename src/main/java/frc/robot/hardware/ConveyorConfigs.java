package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

public class ConveyorConfigs {
    public static void configConveyor(VictorSPX belt, VictorSPX shooter) {
        VictorSPXConfiguration beltConfigs = new VictorSPXConfiguration();

        belt.configAllSettings(beltConfigs);
        belt.setInverted(true);

        VictorSPXConfiguration shooterConfigs = new VictorSPXConfiguration();

        shooter.configAllSettings(shooterConfigs);
        shooter.setInverted(true);
    }
}