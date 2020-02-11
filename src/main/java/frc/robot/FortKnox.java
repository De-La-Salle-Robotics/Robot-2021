package frc.robot;

import frc.robot.autonomous.AutonomousHardware;
import frc.robot.autonomous.AutonomousManager;
import frc.robot.hardware.RobotMap;
import frc.robot.subsystems.*;
import frc.robot.utils.JoystickVals;
import frc.robot.utils.SensorVals; 

public class FortKnox {
    public enum FortKnoxState {
        Disabled,
        Teleoperated,
        Autonomous,
        Test,
    };
    private FortKnoxState _currentState;

    private Drivetrain _drivetrain;
    private Belt _belt;
    private Intake _intake;
    private Climb _climb;
    private Arm _arm;

    private AutonomousHardware _autoHardware;
    private AutonomousManager _autoManager;

    private JoystickVals _joysticks;
    private SensorVals _sensors;
    public FortKnox() {
        RobotMap.initialize();
        
        _drivetrain = new Drivetrain(RobotMap.leftMaster, RobotMap.rightMaster);
        _belt = new Belt(RobotMap.belt, RobotMap.shooter);
        _intake = new Intake(RobotMap.intake);
        _climb = new Climb(RobotMap.climb);
        _arm = new Arm(RobotMap.arm);

        _autoHardware = new AutonomousHardware(RobotMap.leftMaster, 
                                            RobotMap.rightMaster, 
                                            RobotMap.belt, 
                                            RobotMap.shooter);
        _autoManager = new AutonomousManager(_autoHardware);
    
        _joysticks = new JoystickVals(RobotMap.driverJoystick);
        _sensors = new SensorVals(RobotMap.leftMaster, RobotMap.rightMaster);

        _currentState = FortKnoxState.Disabled;
    }
    public void periodicTasks(){
        _joysticks.getJoystickValues();
        _sensors.getSensorValues();

        switch(_currentState)
        {
            case Autonomous:
                /* Run autonomous stuff here */
                _autoManager.runRoutine(_sensors);
                break;
            case Teleoperated:
            case Disabled:
                /* Listen to joysticks and run our mechanisms */
                _belt.beltControl(_joysticks);
                _intake.intakeControl(_joysticks);
                _climb.climbControl(_joysticks);
                _drivetrain.operate(_joysticks);
                _arm.armControl(_joysticks);
                _autoManager.updateRoutines(_joysticks);
                break;
            case Test:
                /* Test does its own stuff */
                break;
        }
    }

    public void changeState(FortKnoxState newState) {
        _currentState = newState;
    }
}

