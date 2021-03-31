package frc.robot.autonomous.routines;

import frc.robot.autonomous.actions.Action;
import frc.robot.autonomous.actions.MMDrive;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;

public class Bounce implements IRoutine {
    /* Distance, Heading, Time */
    private Action[] routines = {
        new MMDrive(100000, 0, 0.5),
        new MMDrive(50000, 90, 0.8),
        new MMDrive(50000, 0, 0.7),
        new MMDrive(-50000, 20, 0.7),
        new MMDrive(-85000, 0, 1.1),
        new MMDrive(-230000, 160, 2.0),
        new MMDrive(-170000, 0, 1.7),
        new MMDrive(190000, 0, 1.2),
        new MMDrive(250000, 180, 2.1),
        new MMDrive(150000, 0, 2.5),
        new MMDrive(-80000, 90, 3),
    };

    public String getName() {
        return "Bounce";
    }

    public void end(RobotState robot) {
        /* Don't end in any state other than what driver wants */
    }

    private int _maxNum;
    private int _currentAction;

    private boolean _finished;

    private String _telemetry;

    public void initialize() {
        /* Initialize variables */
        _finished = false;
        _telemetry = "";
        _maxNum = routines.length;
        _currentAction = 0;
    }

    public void start(SensorVals sensors) {
        /* Reset variables to initial settings */
        _finished = false;
        routines[0].initialize(sensors.totalRobotDist, sensors.rawHeading);
        _currentAction = 0;
    }

    public void onLoop(SensorVals sensors, RobotState robot) {
        routines[_currentAction].run(robot, sensors);
        if (routines[_currentAction].finished()) {
            _currentAction++;
            if (_currentAction == _maxNum) {
                _currentAction--;
                _finished = true;
            } else {
                routines[_currentAction].initialize(routines[_currentAction - 1]);
            }
        }
    }

    public boolean finished() {
        return _finished;
    }

    public String telemetry() {
        return _telemetry;
    }
}
