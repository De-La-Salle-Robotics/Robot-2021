package frc.robot.autonomous.routines;

import frc.robot.utils.RobotState;
import frc.robot.utils.SensorVals;

public interface IRoutine {
    public void initialize();
    public void start(SensorVals sensors);
    public void onLoop(SensorVals sensors, RobotState robot);
    public void end(RobotState robot);
    public boolean finished();
    public String getName();
    public String telemetry();
}