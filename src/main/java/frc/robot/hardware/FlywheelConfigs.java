package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class FlywheelConfigs {
    public static void configFlywheel(TalonFX flywheel) {
        TalonFXConfiguration configuration = new TalonFXConfiguration();
        configuration.slot0.kP = 0.2f;
        configuration.slot0.kF = 0.05f;
        configuration.velocityMeasurementPeriod = VelocityMeasPeriod.Period_10Ms;
        configuration.velocityMeasurementWindow = 32;
        
        flywheel.configAllSettings(configuration);
    }
}
