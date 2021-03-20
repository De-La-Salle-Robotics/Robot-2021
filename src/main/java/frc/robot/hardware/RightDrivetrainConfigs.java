package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.PigeonIMU;

public class RightDrivetrainConfigs {
    private static final double statorlimit = 50;

    public static void configSide(
            TalonFX master, TalonFX slave, TalonFX leftMaster, PigeonIMU pigeon) {
        TalonFXConfiguration masterConfigs = new TalonFXConfiguration();

        masterConfigs.statorCurrLimit.currentLimit = statorlimit;
        masterConfigs.statorCurrLimit.enable = true;
        masterConfigs.statorCurrLimit.triggerThresholdCurrent = statorlimit;
        masterConfigs.statorCurrLimit.triggerThresholdTime = 0;

        masterConfigs.slot0.kP = 0;
        masterConfigs.slot0.kD = 0;
        masterConfigs.slot0.kF = 0.0;

        masterConfigs.slot1.kP = 0.4;
        masterConfigs.slot1.kI = 0.3;
        masterConfigs.slot1.kD = 15;
        masterConfigs.slot1.maxIntegralAccumulator = 120;

        masterConfigs.remoteFilter0.remoteSensorDeviceID = leftMaster.getDeviceID();
        masterConfigs.remoteFilter0.remoteSensorSource = RemoteSensorSource.TalonFX_SelectedSensor;
        masterConfigs.remoteFilter1.remoteSensorDeviceID = pigeon.getDeviceID();
        masterConfigs.remoteFilter1.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw;
        masterConfigs.diff1Term = FeedbackDevice.IntegratedSensor;
        masterConfigs.diff0Term = FeedbackDevice.RemoteSensor0;
        masterConfigs.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorDifference;
        masterConfigs.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor1;

        masterConfigs.motionCruiseVelocity = 1800;
        masterConfigs.motionAcceleration = 3000;

        master.configAllSettings(masterConfigs);
        master.setInverted(true);
        master.setNeutralMode(NeutralMode.Brake);
        master.setSensorPhase(true);
        master.selectProfileSlot(0, 0);
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
