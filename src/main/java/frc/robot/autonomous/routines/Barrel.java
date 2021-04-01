package frc.robot.autonomous.routines;

import frc.robot.autonomous.actions.Action;
import frc.robot.autonomous.actions.MMDrive;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;

public class Barrel implements IRoutine {
    /* Distance, Heading, Time */
    private Action[] routines = {
        // first cone
        new MMDrive(270000, 2.5, 1.6),
        // first cone turn
        new MMDrive(500000, -360, 3.0),
        // second cone drive
        new MMDrive(250000, -15, 1.1),
        // second cone turn
        new MMDrive(450000, 340, 2.7),
        // third cone drive
        new MMDrive(250000, -10, 1.25),
        // third cone turn
        new MMDrive(390000, 220, 2.7),
        // drive home
        new MMDrive(770000, 0, 6),
    };

    public String getName() {
        return "Barrel";
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
