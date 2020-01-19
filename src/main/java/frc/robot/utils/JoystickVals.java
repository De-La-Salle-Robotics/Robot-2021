package frc.robot.utils;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickVals{
    public double wheel;
    public double throttle;
    public boolean armUp;
    public boolean armDown;
    public boolean collectorSuck;
    public boolean collectorBlow;
    public boolean in;
    public boolean out;
    public boolean raise;
    public boolean lower;
    
    private Joystick _driver;
    public JoystickVals (Joystick driver) {
        this._driver = driver;

    }
    public void getJoystickValues() {
        wheel = _driver.getRawAxis(1);
        throttle = _driver.getRawAxis(2);
        armUp = _driver.getRawButton(4);
        armDown = _driver.getRawButton(1);
        collectorSuck = _driver.getRawButton(2);
        collectorBlow = _driver.getRawButton(3);
        in = _driver.getRawButton(5);
        out = _driver.getRawButton(6);
        raise = _driver.getRawButton(7);
        lower = _driver.getRawButton(8);

    }
} 