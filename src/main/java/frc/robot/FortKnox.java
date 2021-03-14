package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.autonomous.AutonomousManager;
import frc.robot.dashboard.MainDashboard;
import frc.robot.hardware.RobotMap;
import frc.robot.subsystems.*;
import frc.robot.utils.RobotState;
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
    private Spinner _spinner;
    private Feeder _feeder;
    private Intake _intake;
    private Arm _arm;

    private Flywheel _flywheel;

    private Limelight _limelight;

    private AutonomousManager _autoManager;

    private MainDashboard _dashboard;

    private RobotState _robot;
    private SensorVals _sensors;

    public FortKnox() {
        RobotMap.initialize();

        UsbCamera src = new UsbCamera("usb0", "/dev/video0");
        src.setResolution(320, 240);
        CameraServer.getInstance().startAutomaticCapture(src);

        _drivetrain = new Drivetrain(RobotMap.leftMaster, RobotMap.rightMaster, RobotMap.pidgey);
        _spinner = new Spinner(RobotMap.spinner);
        _feeder = new Feeder(RobotMap.feeder);
        _flywheel = new Flywheel(RobotMap.flywheel);
        _intake = new Intake(RobotMap.intake);
        _arm = new Arm(RobotMap.arm);
        _limelight = new Limelight();

        _robot = new RobotState(RobotMap.driverJoystick, RobotMap.operatorJoystick);
        _sensors = new SensorVals(RobotMap.leftMaster, RobotMap.rightMaster, RobotMap.pidgey);

        _autoManager = new AutonomousManager();

        _dashboard = new MainDashboard(_sensors, _robot, _autoManager);

        _currentState = FortKnoxState.Disabled;
        _dashboard.start();
    }

    public void periodicTasks() {
        _robot.getJoystickValues();
        _sensors.getSensorValues();

        _limelight.update();

        switch (_currentState) {
            case Autonomous:
                /* Run autonomous stuff here */
                /* _robot state will be overwritten by auto */
                _autoManager.runRoutine(_sensors, _robot);
                /* Fall-through */
            case Teleoperated:
            case Disabled:
                /* Listen to joysticks and run our mechanisms */
                _spinner.spinnerControl(_robot);
                _intake.intakeControl(_robot);
                _feeder.feederControl(_robot);
                _drivetrain.operate(_robot);
                _arm.armControl(_robot);
                _autoManager.updateRoutines(_robot);
                _flywheel.flywheelControl(_robot);
                break;

            case Test:
                /* Test does its own stuff */
                break;
        }
        if (_currentState == FortKnoxState.Disabled) {
            _autoManager.resetAuto();
        }
    }

    public void changeState(FortKnoxState newState) {
        _currentState = newState;
    }
}
