package frc.robot;

import frc.robot.hardware.RobotHardware;
import frc.robot.subsystems.Drivetrain;

public class RobotVault {
    Drivetrain _drivetrain;
    public RobotVault() {
        RobotHardware asfd = new RobotHardware();
        _drivetrain = new Drivetrain(RobotHardware.leftMaster, RobotHardware.rightMaster);
    }

}