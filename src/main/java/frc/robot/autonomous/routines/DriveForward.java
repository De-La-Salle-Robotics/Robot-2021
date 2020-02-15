package frc.robot.autonomous.routines;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.autonomous.AutonomousHardware;
import frc.robot.utils.SensorVals;

public class DriveForward implements IRoutine{
    private int _loops;
    private boolean _finished;

    public void initialize() {
        /* Initialize variables */
        _loops = 0;
        _finished = false;
    }
    public void start(SensorVals sensors) {
        /* Reset variables to initial settings */
        _loops = 0;
        _finished = false;
    }
    public void onLoop(SensorVals sensors, AutonomousHardware hardware) {
        _loops++;
        /* Run for two seconds */
        if(_loops < 100) {
            hardware.leftSide.set(ControlMode.PercentOutput, 0.5);
            hardware.rightSide.set(ControlMode.PercentOutput, 0.5);
        }
        else
        {
            _finished = true; /* We are finished at this point */
        }
    }
    public void end(AutonomousHardware hardware) {
        hardware.leftSide.set(ControlMode.PercentOutput, 0);
        hardware.rightSide.set(ControlMode.PercentOutput, 0);
    }
    public boolean finished() {
        return _finished;
    }

    public String getName() { return "Drive Forward"; }
}