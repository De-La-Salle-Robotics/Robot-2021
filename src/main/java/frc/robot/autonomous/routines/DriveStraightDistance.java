package frc.robot.autonomous.routines;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;
import frc.robot.utils.RobotState.DriveTrainState;

public class DriveStraightDistance implements IRoutine{
    private final double HEADING_CHANGE = 90 * (8192.0 / 360.0);
    private final double DISTANCE_CHANGE = 5 * 4096;

    private boolean _finished;
    private Timer _timer;

    private double _startDistance;
    private double _startHeading;

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
        _startDistance = sensors.totalRobotDist;
        _startHeading = sensors.rawHeading;
    }
    public void onLoop(SensorVals sensors, RobotState robot) {
        /* Run for two seconds */
        if(_timer.get() < 10.0) {
            robot.driveTrainState.set(DriveTrainState.MotionMagic, 
                                    HEADING_CHANGE + _startHeading,
                                    DISTANCE_CHANGE + _startDistance) ;
        }
        else
        {
            _finished = true; /* We are finished at this point */
        }

        _telemetry = sensors.leftSideDist + ":::" + sensors.totalRobotDist;
    }
    public void end(RobotState robot) {
        robot.driveTrainState.set(DriveTrainState.PercentOut, 0, 0);
    }
    public boolean finished() {
        return _finished;
    }

    public String telemetry() {
        return _telemetry;
    }

    public String getName() { return "Drive Straight Distance"; }
}