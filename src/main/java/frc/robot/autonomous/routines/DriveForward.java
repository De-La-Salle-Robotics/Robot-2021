package frc.robot.autonomous.routines;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;

public class DriveForward implements IRoutine{
    private boolean _finished;
    private Timer _timer;

    public void initialize() {
        /* Initialize variables */
        _finished = false;
        _timer = new Timer();
    }
    public void start(SensorVals sensors) {
        /* Reset variables to initial settings */
        _finished = false;
        _timer.start();
    }
    public void onLoop(SensorVals sensors, RobotState robot) {
        /* Run for two seconds */
        if(_timer.get() < 2.0) {
            robot.driveTrainState.leftDriveMode = ControlMode.PercentOutput;
            robot.driveTrainState.rightDriveMode = ControlMode.PercentOutput;
            robot.driveTrainState.leftSide = 0.5;
            robot.driveTrainState.rightSide = 0.5;
        }
        else
        {
            _finished = true; /* We are finished at this point */
        }
    }
    public void end(RobotState robot) {
        robot.driveTrainState.leftDriveMode = ControlMode.PercentOutput;
        robot.driveTrainState.rightDriveMode = ControlMode.PercentOutput;
        robot.driveTrainState.leftSide = 0.5;
        robot.driveTrainState.rightSide = 0.5;
    }
    public boolean finished() {
        return _finished;
    }

    public String getName() { return "Drive Forward"; }
}