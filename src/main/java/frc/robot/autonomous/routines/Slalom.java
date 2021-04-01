package frc.robot.autonomous.routines;

import frc.robot.autonomous.actions.Action;
import frc.robot.autonomous.actions.MMDrive;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;

public class Slalom implements IRoutine {
    /* Distance, Heading, Time */
    private Action[] routines = {
        //first turn
        new MMDrive(120000, 75, 1.2),
        //first counterturn
        new MMDrive(190000, -75, 1.2),
        //first driveout
        new MMDrive(190000, 0, 1.5),
        //second turn
        new MMDrive (100000, -70, 1.0),
        //second allign to cone
        //new MMDrive(60000, 0, 1.0),
        //second turnout
        new MMDrive(550000, 330, 3),
        //second counterturn
        new MMDrive(150000, -80, 1.5),
        //second driveout
        new MMDrive(240000, 0, 2),
        //third turn
        new MMDrive(120000, -75, 1.2),
        //third allign to cone
        new MMDrive(60000, 0, 0.7),
        //third counterturn
        new MMDrive(90000, 75, 1.2),


    };



    public String getName() {
        return "Slalom";
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
