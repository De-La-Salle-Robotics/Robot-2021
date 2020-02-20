package frc.robot.autonomous.routines;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;
import frc.robot.utils.RobotState.DriveTrainState;
import frc.robot.utils.RobotState.PCState;

public class SpitAndDrive implements IRoutine{
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
        /* Shoot for 2 seconds */
        if(_timer.get() < 2.0) {
            robot.powerCellState = PCState.Shoot;
        }
        /* Drive for 2 more seconds */
        else if(_timer.get() < 4.0) {
            robot.driveTrainState.set(DriveTrainState.PercentOut, 0.5, 0.5);
        }
        else {
            _finished = true; /* We are finished at this point */
        }
    }
    public void end(RobotState robot) {
        robot.driveTrainState.set(DriveTrainState.PercentOut, 0, 0);
        robot.powerCellState = PCState.WaitUp;
    }
    public boolean finished() {
        return _finished;
    }

    public String telemetry() {
        return "" + _timer.get();
    }

    public String getName() { return "Spit and Drive"; }
}