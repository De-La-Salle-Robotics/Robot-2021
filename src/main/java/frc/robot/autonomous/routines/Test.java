package frc.robot.autonomous.routines;

import frc.robot.autonomous.actions.Action;
import frc.robot.autonomous.actions.MMDrive;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;

public class Test implements IRoutine {
    /* Distance, Heading, Time */
    private Action[] routines = {
        new MMDrive(-22000, 0, 3.0),
    };

    public String getName() {
        return "Score And Leave";
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
