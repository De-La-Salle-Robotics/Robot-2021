package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.PigeonState;

public class SensorVals {
    private BaseTalon _leftTalon;
    private BaseTalon _rightTalon;
    private PigeonIMU _pidgey;
    private TalonFX _flywheel;

    public double leftSideDist;
    public double totalRobotDist;
    public double heading;
    public double rawHeading;
    public PigeonState pidgeyState;
    public double flywheelSpeed;

    public SensorVals(
            BaseTalon leftMaster, BaseTalon rightMaster, PigeonIMU pigeon, TalonFX flywheel) {
        _leftTalon = leftMaster;
        _rightTalon = rightMaster;
        _pidgey = pigeon;
        _flywheel = flywheel;
    }

    public void getSensorValues() {
        leftSideDist = _leftTalon.getSelectedSensorPosition();
        totalRobotDist = _rightTalon.getSelectedSensorPosition();

        double[] ypr = new double[3];
        _pidgey.getYawPitchRoll(ypr);
        heading = ypr[0]; /* This is yaw */
        rawHeading = heading * (8192.0 / 360.0);
        pidgeyState = _pidgey.getState();
        flywheelSpeed = _flywheel.getSelectedSensorVelocity();
    }
}
