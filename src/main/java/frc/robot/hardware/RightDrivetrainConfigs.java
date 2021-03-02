package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFXPIDSetConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;


public class RightDrivetrainConfigs {

    public static void configSide(TalonFX master, TalonFX slave, TalonFX leftMaster, PigeonIMU pigeon) {
        TalonFXConfiguration masterConfigs = new TalonFXConfiguration();

        masterConfigs.slot0.kP = 2;
        masterConfigs.slot0.kD = 5;
        masterConfigs.slot0.kF = 0.37749077490774907749077490774908;
        masterConfigs.slot1.kP = 3;
        masterConfigs.slot1.kD = 50;

        masterConfigs.slot0 = TalonFXFeedbackDevice.IntegratedSensor.toFeedbackDevice();
        masterConfigs.remoteFilter0 = leftMaster.getDeviceID();
        masterConfigs.remoteFilter1.remoteSensorSource = TalonFXFeedbackDevice.IntegratedSensor.toFeedbackDevice();
        masterConfigs.sum0Term = TalonFXFeedbackDevice.IntegratedSensor;
        masterConfigs.sum1Term = TalonFXFeedbackDevice.getTalonFXSensorCollection;
        masterConfigs.primaryPID.selectedFeedbackCoefficient = 0.5;

        masterConfigs.auxiliaryPID.selectedFeedbackSensor = TalonFXSensorCollection;
        masterConfigs.remoteFilter0.remoteSensorDeviceID = pigeon.getDeviceID();
        masterConfigs.remoteFilter0.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw;

        masterConfigs.motionCruiseVelocity = 1800;
        masterConfigs.motionAcceleration = 3000;

        master.configAllSettings(masterConfigs);
        master.setInverted(true);
        master.setNeutralMode(NeutralMode.Brake);
        master.setSensorPhase(true);
        master.selectProfileSlot(1, 1);

        TalonFXConfiguration slaveConfigs = new TalonFXConfiguration();

        slave.configAllSettings(slaveConfigs);

        slave.follow(master);
        slave.setInverted(TalonFXInvertType.FollowMaster);
        slave.setNeutralMode(NeutralMode.Brake);
    }
}