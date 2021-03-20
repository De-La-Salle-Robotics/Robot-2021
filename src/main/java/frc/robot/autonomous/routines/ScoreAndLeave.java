package frc.robot.autonomous.routines;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.DriveTrainState;
import frc.robot.utils.RobotState.PCState;
import frc.robot.utils.SensorVals;

public class ScoreAndLeave implements IRoutine {
    private final double HEADING_CHANGE_1 = 0;
    private final double DISTANCE_CHANGE_1 = -22000;
    private final double HEADING_CHANGE_2 = 45 * 8192.0 / 360.0;
    private final double DISTANCE_CHANGE_2 = 20000;
    private final double HEADING_CHANGE_3 = -47 * 8192.0 / 360.0;
    private final double DISTANCE_CHANGE_3 = 18000;
    private final double HEADING_CHANGE_4 = 0;
    private final double DISTANCE_CHANGE_4 = 68000;

    private boolean _finished;
    private Timer _timer;

    private double _distanceOffset;
    private double _headingOffset;

    private String _telemetry;

    private int _lastState;

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
        _lastState = 0;
    }

    public void onLoop(SensorVals sensors, RobotState robot) {
        /* Run for two seconds */
        if (_timer.get() < 3.0) {
            robot.driveTrainState.set(
                    DriveTrainState.MotionMagic,
                    HEADING_CHANGE_1 + _headingOffset,
                    DISTANCE_CHANGE_1 + _distanceOffset);
            _lastState = 0;
        } else if (_timer.get() < 4.0) {
            robot.powerCellState = PCState.Index;
            _lastState = 1;
        } else if (_timer.get() < 6.5) {
            if (_lastState == 1) {
                _headingOffset += HEADING_CHANGE_1;
                _distanceOffset += DISTANCE_CHANGE_1;
            }
            robot.driveTrainState.set(
                    DriveTrainState.MotionMagic,
                    HEADING_CHANGE_2 + _headingOffset,
                    DISTANCE_CHANGE_2 + _distanceOffset);
            _lastState = 2;
        } else if (_timer.get() < 9.0) {
            if (_lastState == 2) {
                _headingOffset += HEADING_CHANGE_2;
                _distanceOffset += DISTANCE_CHANGE_2;
            }
            robot.driveTrainState.set(
                    DriveTrainState.MotionMagic,
                    HEADING_CHANGE_3 + _headingOffset,
                    DISTANCE_CHANGE_3 + _distanceOffset);
            _lastState = 3;
        } else if (_timer.get() < 15.0) {
            if (_lastState == 3) {
                _headingOffset += HEADING_CHANGE_3;
                _distanceOffset += DISTANCE_CHANGE_3;
            }
            robot.driveTrainState.set(
                    DriveTrainState.MotionMagic,
                    HEADING_CHANGE_4 + _headingOffset,
                    DISTANCE_CHANGE_4 + _distanceOffset);
            robot.powerCellState = PCState.Suck;
            _lastState = 4;
        } else {

            _finished = true; /* We are finished at this point */
        }

        _telemetry = sensors.leftSideDist + ":::" + sensors.totalRobotDist;
    }

    public void end(RobotState robot) {
        robot.driveTrainState.set(DriveTrainState.PercentOut, 0, 0);
        robot.powerCellState = PCState.Wait;
    }

    public boolean finished() {
        return _finished;
    }

    public String telemetry() {
        return _telemetry;
    }

    public String getName() {
        return "Score And Leave";
    }
}
