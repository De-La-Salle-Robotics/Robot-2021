package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;

public class SensorVals {
    private BaseTalon _leftTalon;
    private BaseTalon _rightTalon;

    public double leftSideDist;
    public double rightSideDist;

    public SensorVals(BaseTalon leftMaster, BaseTalon rightMaster) {
        _leftTalon = leftMaster;
        _rightTalon = rightMaster;
    }

    public void getSensorValues() {
        leftSideDist = _leftTalon.getSelectedSensorPosition();
        rightSideDist = _rightTalon.getSelectedSensorPosition();
    }
}