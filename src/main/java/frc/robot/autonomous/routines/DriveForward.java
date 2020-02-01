package frc.robot.autonomous.routines;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.autonomous.AutonomousHardware;
import frc.robot.utils.SensorVals;

public class DriveForward implements IRoutine{
    private int _loops;

    public void initialize() { 
        _loops = 0;
    }
    public void start(SensorVals sensors) {
        _loops = 0;
    }
    public void onLoop(SensorVals sensors, AutonomousHardware hardware) {
        _loops++;
        if(_loops < 100) {
            hardware.leftSide.set(ControlMode.PercentOutput, 0.5);
            hardware.rightSide.set(ControlMode.PercentOutput, 0.5);
        }
        else
        {
            end(hardware);
        }
    }
    public void end(AutonomousHardware hardware) {
        hardware.leftSide.set(ControlMode.PercentOutput, 0);
        hardware.rightSide.set(ControlMode.PercentOutput, 0);
    }
    public boolean finished() {
        return _loops > 100; /* run for 2 seconds */
    }

    public String getName() { return "Drive Forward"; }
}