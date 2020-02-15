package frc.robot.utils;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickVals{
    public enum PCState {
        WaitUp,
        WaitDown,
        Suck,
        Blow,
        Shoot,
    }
    public enum HangState {
        Deploy,
        Retract,
        Hang,
        Nothing,
    }
    public enum AutonState {
        NextAuto,
        PreviousAuto,
        AutoChanged,
        ButtonReleased,
    }

    public double wheel;
    public double throttle;

    public PCState powerCellState;
    public HangState hanger;
    public AutonState routine;
    
    private Joystick _driver;
    private Joystick _operator;
    public JoystickVals (Joystick driver, Joystick operator) {
        _driver = driver;
        _operator = operator;

        /* Initialize states */
        powerCellState = PCState.WaitUp;
        hanger = HangState.Nothing;
        routine = AutonState.AutoChanged;
    }
    public void getJoystickValues() {
        /* Drive base */
        wheel = _driver.getRawAxis(1);
        throttle = -_driver.getRawAxis(4); /* Throttle is negated */
        
        /* If we press arm down, go down */
        if(_operator.getRawButton(4)) {
            powerCellState = PCState.WaitDown;
        /* If we press suck, go into suck */
        } else if (_operator.getRawButton(7)) {
            powerCellState = PCState.Suck;
        /* If we press blow, go into blow */
        } else if (_operator.getRawButton(6)) {
            powerCellState = PCState.Blow;
        /* If we press shoot, go into shoot */
        } else if (_operator.getRawButton(8)) {
            powerCellState = PCState.Shoot;
        /* If we press arm up, go into waitup */
        } else if (_operator.getRawButton(2)) {
            powerCellState = PCState.WaitUp;
        /* If we didn't press anything, go into wait based on last state */
        } else {
            /* If we were last sucking or waiting down, stay down */
            if(powerCellState == PCState.Suck || 
               powerCellState == PCState.WaitDown) {
                powerCellState = PCState.WaitDown;
            /* Otherwise keep the arm up */
            } else {
                powerCellState = PCState.WaitUp;
            }
        }

        /* If we press deploy, start to deploy */
        if(_driver.getRawButton(1)) {
            hanger = HangState.Deploy;
        /* If we press retract, go into retract */
        } else if(_driver.getRawButton(2)) {
            hanger = HangState.Retract;
        /* If we press hang, go into hang */
        } else if(_driver.getRawButton(3)) {
            hanger = HangState.Hang;
        /* Otherwise we do nothing */
        } else {
            hanger = HangState.Nothing;
        }

        if(_driver.getPOV() == 90) {
            routine = AutonState.NextAuto;
        } else if (_driver.getPOV() == 270) {
            routine = AutonState.PreviousAuto;
        } else if (_driver.getPOV() == -1) {
            routine = AutonState.ButtonReleased;
        }
    }
} 