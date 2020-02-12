package frc.robot.utils;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickVals{
    public double wheel;
    public double throttle;
    public boolean shooterFire;
    public boolean shooterReload;
    public boolean collectorSuck;
    public boolean collectorBlow;
    public boolean in;
    public boolean out;
    public boolean raise;
    public boolean lower;
    public boolean armUp;
    public boolean armDown;
    public boolean winchDown;

    public boolean nextRoutine;
    public boolean previousRoutine;
    
    private Joystick _driver;
    public JoystickVals (Joystick driver) {
        this._driver = driver;

    }
    public void getJoystickValues() {
        /* Drive base */
        wheel = _driver.getRawAxis(1);
        throttle = -_driver.getRawAxis(4); /* Throttle is negated */
        /* Shooter */
        shooterFire = _driver.getRawButton(4);
        shooterReload = _driver.getRawButton(1);
        /* Collector */
        collectorSuck = _driver.getRawButton(2);
        collectorBlow = _driver.getRawButton(3);
        in = _driver.getRawButton(5);
        out = _driver.getRawButton(6);
        /* Climber */
        raise = _driver.getRawButton(7);
        lower = _driver.getRawButton(8);
        winchDown = _driver.getRawButton(11);
        /* Arm */
        armUp = _driver.getRawButton(9);
        armDown = _driver.getRawButton(10);

        nextRoutine = _driver.getPOV() == 90;
        previousRoutine = _driver.getPOV() == 270;
    }
} 