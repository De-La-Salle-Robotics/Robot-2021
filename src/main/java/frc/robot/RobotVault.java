package frc.robot;

import frc.robot.hardware.RobotMap;
import frc.robot.subsystems.*;

public class RobotVault {
    Drivetrain _drivetrain;
    Arm _arm;
    Belt _belt;
    Intake _intake;
    public RobotVault() {
        _arm = new Arm(RobotMap.arm);
        _drivetrain = new Drivetrain(RobotMap.leftMaster, RobotMap.rightMaster);
    }

}