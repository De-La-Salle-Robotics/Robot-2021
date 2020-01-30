package frc.robot;

import frc.robot.hardware.RobotMap;
import frc.robot.subsystems.*;
import frc.robot.utils.JoystickVals; 

public class FortKnox {
    private Drivetrain _drivetrain;
    private Belt _belt;
    private Intake _intake;
    private Climb _climb;
    private Arm _arm;

    private JoystickVals _joysticks;
    public FortKnox() {             
        _drivetrain = new Drivetrain(RobotMap.leftMaster, RobotMap.rightMaster);
        _belt = new Belt(RobotMap.belt, RobotMap.shooter);
        _intake = new Intake(RobotMap.intake);
        _climb = new Climb(RobotMap.climb);
        _arm = new Arm(RobotMap.arm);
    
    }
    public void periodicTasks(){
        _joysticks.getJoystickValues();

        _belt.beltControl(_joysticks);
        _intake.intakeControl(_joysticks);
        _climb.climbControl(_joysticks);
        _drivetrain.operate(_joysticks);
        _arm.armControl(_joysticks);
    }
}

