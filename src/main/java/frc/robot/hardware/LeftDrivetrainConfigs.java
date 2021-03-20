package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class LeftDrivetrainConfigs {
    private static final double statorlimit = 50;

    public static void configSide(TalonFX master, TalonFX slave) {
        TalonFXConfiguration masterConfigs = new TalonFXConfiguration();

        masterConfigs.statorCurrLimit.currentLimit = statorlimit;
        masterConfigs.statorCurrLimit.enable = true;
        masterConfigs.statorCurrLimit.triggerThresholdCurrent = statorlimit;
        masterConfigs.statorCurrLimit.triggerThresholdTime = 0;

        master.configAllSettings(masterConfigs);
        master.setInverted(false);
        master.setNeutralMode(NeutralMode.Brake);
        master.setSensorPhase(true);

        TalonFXConfiguration slaveConfigs = new TalonFXConfiguration();

        slaveConfigs.statorCurrLimit.currentLimit = statorlimit;
        slaveConfigs.statorCurrLimit.enable = true;
        slaveConfigs.statorCurrLimit.triggerThresholdCurrent = statorlimit;
        slaveConfigs.statorCurrLimit.triggerThresholdTime = 0;

        slave.configAllSettings(slaveConfigs);

        slave.follow(master);
        slave.setInverted(TalonFXInvertType.FollowMaster);
        slave.setNeutralMode(NeutralMode.Brake);
    }
}
