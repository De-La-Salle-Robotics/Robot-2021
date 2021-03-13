package frc.robot.utils;

import edu.wpi.first.wpilibj.Joystick;

public class RobotState{
    public enum PCState {
        WaitUp,
        WaitDown,
        Suck,
        Blow,
        Gulp,
        Spit, 
        Discharge,
    }
    
    public enum AutonState {
        NextAuto,
        PreviousAuto,
        AutoChanged,
        ButtonReleased,
    }
    public enum DriveTrainState {
        PercentOut,
        Position,
        MotionMagic,
    }
    public class DTStruct {
        public DriveTrainState state;
        public double leftSide;
        public double rightSide;

        @Override
        public String toString() {
            return state.toString() + "  " + leftSide + " " + rightSide;
        }

        public void set(DriveTrainState state, double left, double right) {
            this.state = state;
            leftSide = left;
            rightSide = right;
        }
    };
    
    public DTStruct driveTrainState;
    public PCState powerCellState;
    public AutonState routine;

    public boolean clearSensors;
    
    private Joystick _driver;
    private Joystick _operator;
    public RobotState (Joystick driver, Joystick operator) {
        _driver = driver;
        _operator = operator;

        /* Initialize states */
        driveTrainState = new DTStruct();
        powerCellState = PCState.WaitUp;
        routine = AutonState.AutoChanged;

        clearSensors = false;
    }
    public void getJoystickValues() {
        /* Drive base */
        double throt = -_driver.getRawAxis(1);
        double wheel = _driver.getRawAxis(4) * 0.5; /* Throttle is negated */
        driveTrainState.set(DriveTrainState.PercentOut, throt + wheel, throt - wheel);
        
        /* If we press arm down, go down */
        if(_operator.getRawButton(4)) {
            System.out.println("Pressed Wait Down");
            powerCellState = PCState.WaitDown;
        /* If we press suck, go into suck */
        } else if (_operator.getRawButton(7)) {
            powerCellState = PCState.Suck;
        /* If we press blow, go into blow */
        } else if (_operator.getRawButton(3)) {
            powerCellState = PCState.Blow;
        /* If we press gulp, go into gulp */
        } else if (_operator.getRawButton(5)) {
            powerCellState = PCState.Gulp;
        /* If we press discharge, go into discharge */
        } else if (_operator.getRawButton(8)) {
            powerCellState = PCState.Discharge;
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

     
        if(routine != AutonState.AutoChanged) {
            if(_driver.getPOV() == 90) {
                routine = AutonState.NextAuto;
            } else if (_driver.getPOV() == 270) {
                routine = AutonState.PreviousAuto;
            }
        } else if (_driver.getPOV() == -1) {
            routine = AutonState.ButtonReleased;
        }

        if(_driver.getPOV() == 180) {
            clearSensors = true;
        } else {
            clearSensors = false;
        }
    }
} 