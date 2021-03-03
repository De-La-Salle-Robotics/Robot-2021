package frc.robot.autonomous.actions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;
import frc.robot.utils.RobotState.PCState;

public class Score implements Action {
    private double _dist;
    private double _head;
    private double _time;

    private Timer _timer;

    public Score(double timeElapsed) {
        _time = timeElapsed;
        _timer = new Timer();
    }

    public double distElapsed() { return _dist; }
    public double headingElapsed() { return _head; }
    public void initialize(double forward, double heading) { 
        _dist = forward; 
        _head = heading; 
        _timer.start(); 
    }
    public void initialize(Action prevAction) { 
        initialize(prevAction.distElapsed(), prevAction.headingElapsed()); 
    }
    public void run(RobotState state, SensorVals sensors) { state.powerCellState = PCState.Gulp; }
    
    public boolean finished() { return _timer.get() >= _time; }
}