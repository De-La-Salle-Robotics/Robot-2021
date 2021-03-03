package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX; 
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class LeftDrivetrainConfigs {

    public static void configSide(TalonFX master, TalonFX slave) {
        TalonFXConfiguration masterConfigs = new TalonFXConfiguration();

        master.configAllSettings(masterConfigs);
        master.setInverted(false);
        master.setNeutralMode(NeutralMode.Brake);
        master.setSensorPhase(true);

        TalonFXConfiguration slaveConfigs = new TalonFXConfiguration();

        slave.configAllSettings(slaveConfigs);

        slave.follow(master);
        slave.setInverted(TalonFXInvertType.FollowMaster);
        slave.setNeutralMode(NeutralMode.Brake);
    }
}