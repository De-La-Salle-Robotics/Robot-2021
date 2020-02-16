package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;

public class RobotState{
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
    public class DrivetrainState {
        public ControlMode leftDriveMode;
        public ControlMode rightDriveMode;
        public double leftSide;
        public double rightSide;
    };
    
    public DrivetrainState driveTrainState;
    public PCState powerCellState;
    public HangState hanger;
    public AutonState routine;
    
    private Joystick _driver;
    private Joystick _operator;
    public RobotState (Joystick driver, Joystick operator) {
        _driver = driver;
        _operator = operator;

        /* Initialize states */
        driveTrainState = new DrivetrainState();
        powerCellState = PCState.WaitUp;
        hanger = HangState.Nothing;
        routine = AutonState.AutoChanged;
    }
    public void getJoystickValues() {
        /* Drive base */
        double throt = _driver.getRawAxis(1);
        double wheel = -_driver.getRawAxis(4); /* Throttle is negated */
        driveTrainState.leftDriveMode = ControlMode.PercentOutput;
        driveTrainState.rightDriveMode = ControlMode.PercentOutput;
        driveTrainState.leftSide = throt + wheel;
        driveTrainState.rightSide = throt - wheel;
        
        /* If we press suck, go into suck */
        if (_operator.getRawButton(1)) {
            powerCellState = PCState.Suck;
        /* If we press blow, go into blow */
        } else if (_operator.getRawButton(2)) {
            powerCellState = PCState.Blow;
        /* If we press shoot, go into shoot */
        } else if (_operator.getRawButton(3)) {
            powerCellState = PCState.Shoot;
        /* If we press arm up, go into waitup */
        } else if (_operator.getRawButton(4)) {
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
        if(_operator.getRawButton(4)) {
            hanger = HangState.Deploy;
        /* If we press retract, go into retract */
        } else if(_operator.getRawButton(5)) {
            hanger = HangState.Retract;
        /* If we press hang, go into hang */
        } else if(_operator.getRawButton(6)) {
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