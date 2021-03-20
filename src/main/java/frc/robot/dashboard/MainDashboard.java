package frc.robot.dashboard;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.AutonomousManager;
import frc.robot.subsystems.Limelight;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;

;

public class MainDashboard extends Thread {
    SensorVals _sensors;
    RobotState _robotState;
    AutonomousManager _autoManager;
    Limelight _limelight;

    private boolean _runThread;

    public MainDashboard(
            SensorVals sensors,
            RobotState robotState,
            AutonomousManager autonomousManager,
            Limelight limelight) {
        _sensors = sensors;
        _robotState = robotState;
        _autoManager = autonomousManager;
        _limelight = limelight;
        _runThread = true;
    }

    @Override
    public void run() {
        while (_runThread) {
            /* We will wait 100 ms before updating dashboard */
            Timer.delay(0.1);

            /* Display selected auto name */
            SmartDashboard.putString("Auton", _autoManager.getAutoName());
            SmartDashboard.putString("Auton Info", _autoManager.telemetry());
            SmartDashboard.putString("Auto State", _robotState.routine.toString());

            /* Display Robot States */
            SmartDashboard.putString("Robot State", _robotState.powerCellState.toString());
            SmartDashboard.putString("Drivetrain State", _robotState.driveTrainState.toString());

            /* Display sensor information */
            SmartDashboard.putNumber("Left Side Distance", _sensors.leftSideDist);
            SmartDashboard.putNumber("Right Side Distance", _sensors.totalRobotDist);
            SmartDashboard.putString("Pigeon State", _sensors.pidgeyState.toString());
            SmartDashboard.putNumber("Direction", _sensors.heading);

            /* Display Limelight Info */
            SmartDashboard.putString("Limelight", _limelight.getTarget().toString());
            SmartDashboard.putNumber("Flywheel Speed", _sensors.flywheelSpeed);
            SmartDashboard.putString("Zone", _robotState.shooterSpeed.toString());

            /* Display match information */
            SmartDashboard.putNumber("Match Time", Timer.getMatchTime());
            SmartDashboard.putString("Game Data", DriverStation.getInstance().getGameSpecificMessage());
        }
    }

    public void stopThread() {
        _runThread = false;
    }
}
