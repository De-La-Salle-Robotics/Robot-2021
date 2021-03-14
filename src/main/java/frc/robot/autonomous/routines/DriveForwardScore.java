package frc.robot.autonomous.routines;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.DriveTrainState;
import frc.robot.utils.RobotState.PCState;
import frc.robot.utils.SensorVals;

public class DriveForwardScore implements IRoutine {
    private final double HEADING_CHANGE_1 = 0;
    private final double DISTANCE_CHANGE_1 = -22000;

    private boolean _finished;
    private Timer _timer;

    private double _distanceOffset;
    private double _headingOffset;

    private String _telemetry;

    public void initialize() {
        /* Initialize variables */
        _finished = false;
        _timer = new Timer();
        _telemetry = "";
    }

    public void start(SensorVals sensors) {
        /* Reset variables to initial settings */
        _finished = false;
        _timer.start();
        _distanceOffset = sensors.totalRobotDist;
        _headingOffset = sensors.rawHeading;
    }

    public void onLoop(SensorVals sensors, RobotState robot) {
        /* Run for two seconds */
        if (_timer.get() < 3.0) {
            robot.driveTrainState.set(
                    DriveTrainState.MotionMagic,
                    HEADING_CHANGE_1 + _headingOffset,
                    DISTANCE_CHANGE_1 + _distanceOffset);
        } else if (_timer.get() < 5.0) {
            robot.powerCellState = PCState.Index;
        } else {
            _finished = true; /* We are finished at this point */
        }

        _telemetry = sensors.leftSideDist + ":::" + sensors.totalRobotDist;
    }

    public void end(RobotState robot) {
        robot.driveTrainState.set(DriveTrainState.PercentOut, 0, 0);
        robot.powerCellState = PCState.WaitUp;
    }

    public boolean finished() {
        return _finished;
    }

    public String telemetry() {
        return _telemetry;
    }

    public String getName() {
        return "Drive Forward Score";
    }
}
