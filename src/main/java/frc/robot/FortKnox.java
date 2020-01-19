package frc.robot;

import frc.robot.hardware.RobotMap;
import frc.robot.subsystems.*;
import frc.robot.utils.JoystickVals; 

public class FortKnox {
    private Drivetrain _drivetrain;
    private Arm _arm;
    private Belt _belt;
    private Intake _intake;
    private Climb _climb;

    private JoystickVals _joysticks;
    public FortKnox() {             
        _arm = new Arm(RobotMap.arm);
        _drivetrain = new Drivetrain(RobotMap.leftMaster, RobotMap.rightMaster);
        _belt = new Belt(RobotMap.belt);
        _intake = new Intake(RobotMap.intake);
        _climb = new Climb(RobotMap.climb);

    }
    public void periodicTasks(){
        _joysticks.getJoystickValues();

        _arm.armControl(_joysticks);
        _belt.beltControl(_joysticks);
        _intake.intakeControl(_joysticks);
        _climb.climbControl(_joysticks);
        _drivetrain.operate(_joysticks);
    }
}

