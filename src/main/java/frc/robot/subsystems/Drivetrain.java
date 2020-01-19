package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;

import frc.robot.utils.JoystickVals;

public class Drivetrain {
    private BaseTalon _leftMaster;
    private BaseTalon _rightMaster;

    public Drivetrain(BaseTalon leftMaster, BaseTalon rightMaster) {
        _leftMaster = leftMaster;
        _rightMaster = rightMaster;

    }

    public void operate(JoystickVals joysticks) {
        double leftSide = joysticks.throttle - joysticks.wheel;
        double rightSide = joysticks.throttle + joysticks.wheel;

        _leftMaster.set(ControlMode.PercentOutput, leftSide);
        _rightMaster.set(ControlMode.PercentOutput, rightSide);
    }
}