package frc.robot.autonomous.routines;

import frc.robot.autonomous.AutonomousHardware;
import frc.robot.utils.SensorVals;

public interface IRoutine {
    public void initialize();
    public void start(SensorVals sensors);
    public void onLoop(SensorVals sensors, AutonomousHardware hardware);
    public void end(AutonomousHardware hardware);
    public boolean finished();
    public String getName();
}