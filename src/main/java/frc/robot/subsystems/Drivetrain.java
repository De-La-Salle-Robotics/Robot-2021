package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Drivetrain {
    private TalonSRX _leftMaster;
    private TalonSRX _rightMaster;

    public Drivetrain(TalonSRX leftMaster, TalonSRX rightMaster) {
        _leftMaster = leftMaster;
        _rightMaster = rightMaster;

    }

    public void Operate(double throttle, double wheel) {
        double leftSide = throttle - wheel;
        double rightSide = throttle + wheel;

        _leftMaster.set(ControlMode.PercentOutput, leftSide);
        _rightMaster.set(ControlMode.PercentOutput, rightSide);
    }
}