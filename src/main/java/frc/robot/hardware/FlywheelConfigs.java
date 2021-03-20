package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class FlywheelConfigs {
    public static void configFlywheel(TalonFX flywheel) {
        TalonFXConfiguration configuration = new TalonFXConfiguration();
        configuration.slot0.kP = 0.7;
        configuration.slot0.kI = 0.005;
        configuration.slot0.kF = 0.047;
        configuration.slot0.integralZone = 800;

        configuration.velocityMeasurementPeriod = VelocityMeasPeriod.Period_10Ms;
        configuration.velocityMeasurementWindow = 32;

        flywheel.configAllSettings(configuration);
    }
}
