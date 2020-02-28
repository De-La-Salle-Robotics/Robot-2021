package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import frc.robot.utils.RobotState;

public class Drivetrain {
    private TalonSRX _leftMaster;
    private TalonSRX _rightMaster;
    private PigeonIMU _pidgey;

    public Drivetrain(TalonSRX leftMaster, TalonSRX rightMaster, PigeonIMU pigeon) {
        _leftMaster = leftMaster;
        _rightMaster = rightMaster;
        _pidgey = pigeon;
    }

    public void operate(RobotState joysticks) {
        /* Check if we should reset sensors */
        if(joysticks.clearSensors) {
            _leftMaster.getSensorCollection().setQuadraturePosition(0, 0);
            _rightMaster.getSensorCollection().setQuadraturePosition(0, 0);
            _pidgey.setYaw(0);
        }

        switch(joysticks.driveTrainState.state) {
            case PercentOut:
                _leftMaster.set(ControlMode.PercentOutput, joysticks.driveTrainState.leftSide);
                _rightMaster.set(ControlMode.PercentOutput, joysticks.driveTrainState.rightSide);
                break;
            case Position:
                break;
            case MotionMagic:
                _leftMaster.follow(_rightMaster, FollowerType.AuxOutput1);
                _rightMaster.set(ControlMode.MotionMagic, joysticks.driveTrainState.rightSide,
                                DemandType.AuxPID, joysticks.driveTrainState.leftSide);
                break;
            case SlowDrive:
                _leftMaster.set(ControlMode.PercentOutput, joysticks.driveTrainState.leftSide * 0.3);
                _rightMaster.set(ControlMode.PercentOutput, joysticks.driveTrainState.rightSide * 0.3);
                break;
        }
    }
}