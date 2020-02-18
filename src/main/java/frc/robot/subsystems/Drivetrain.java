package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;

import frc.robot.utils.RobotState;

public class Drivetrain {
    private BaseTalon _leftMaster;
    private BaseTalon _rightMaster;

    public Drivetrain(BaseTalon leftMaster, BaseTalon rightMaster) {
        _leftMaster = leftMaster;
        _rightMaster = rightMaster;

    }

    public void operate(RobotState joysticks) {
        _leftMaster.set(joysticks.driveTrainState.leftDriveMode, joysticks.driveTrainState.leftSide);
        _rightMaster.set(joysticks.driveTrainState.rightDriveMode, joysticks.driveTrainState.rightSide);
    }
}