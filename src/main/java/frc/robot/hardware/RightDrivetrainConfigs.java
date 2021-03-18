package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.PigeonIMU;

public class RightDrivetrainConfigs {
    private final static double statorlimit = 50;

    public static void configSide(
            TalonFX master, TalonFX slave, TalonFX leftMaster, PigeonIMU pigeon) {
        TalonFXConfiguration masterConfigs = new TalonFXConfiguration();

        masterConfigs.statorCurrLimit.currentLimit = statorlimit;
        masterConfigs.statorCurrLimit.enable = true;
        masterConfigs.statorCurrLimit.triggerThresholdCurrent = statorlimit;
        masterConfigs.statorCurrLimit.triggerThresholdTime = 0;

        masterConfigs.slot0.kP = 2;
        masterConfigs.slot0.kD = 5;
        masterConfigs.slot0.kF = 0.37749077490774907749077490774908;
        masterConfigs.slot1.kP = 3;
        masterConfigs.slot1.kD = 50;

        masterConfigs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

        masterConfigs.motionCruiseVelocity = 1800;
        masterConfigs.motionAcceleration = 3000;

        master.configAllSettings(masterConfigs);
        master.setInverted(true);
        master.setNeutralMode(NeutralMode.Brake);
        master.setSensorPhase(true);
        master.selectProfileSlot(1, 1);

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
