package frc.robot.utils;

import edu.wpi.first.wpilibj.Joystick;

public class RobotState {
    public enum PCState {
        /** Not doing anything */
        Wait,
        /** Collector is down Intake and disturber is running */
        Suck,
        /** Collector is up Intake is running in reverse */
        Blow,
        /** Run the feeder to index a ball */
        Index,
        /** Feeder runs in reverse to remove balls from tower */
        Spit,
    }

    public enum ArmState {
        Up,
        Down
    }

    public enum ShooterState {
        /** We don't want the shooter running right now */
        Off,
        /** We are anticipating a shot, so let's spin the flywheel up to speed */
        PrepareShoot,
        /** We want to shoot and we're at speed, let's shoot */
        Shooting
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
    }
    ;

    public DTStruct driveTrainState;
    public PCState powerCellState;
    public ShooterState shooterState;
    public AutonState routine;
    public ArmState armState;

    public boolean clearSensors;

    private Joystick _driver;
    private Joystick _operator;

    public RobotState(Joystick driver, Joystick operator) {
        _driver = driver;
        _operator = operator;

        /* Initialize states */
        driveTrainState = new DTStruct();
        powerCellState = PCState.Wait;
        shooterState = ShooterState.Off;
        routine = AutonState.AutoChanged;

        clearSensors = false;
    }

    public void getJoystickValues() {
        /* Drive base */
        double throt = -_driver.getRawAxis(1);
        double wheel = _driver.getRawAxis(4) * 0.5; /* Throttle is negated */
        driveTrainState.set(DriveTrainState.PercentOut, throt + wheel, throt - wheel);

        /* If we press arm down, go down */
        if (_operator.getRawButton(4)) {
            System.out.println("Pressed Wait Down");
            powerCellState = PCState.Wait;
            armState = ArmState.Down;
            /* If we press suck, go into suck */
        } else if (_operator.getRawButton(7)) {
            powerCellState = PCState.Suck;
            armState = ArmState.Down;
            /* If we press blow, go into blow */
        } else if (_operator.getRawButton(3)) {
            powerCellState = PCState.Blow;
            armState = ArmState.Up;
            /* If we press gulp, go into gulp */
        } else if (_operator.getRawButton(6)) {
            powerCellState = PCState.Index;
            /* If we press arm up, go into waitup */
        } else if (_operator.getRawButton(2)) {
            powerCellState = PCState.Wait;
            armState = ArmState.Up;
            /* If we didn't press anything, go into wait based on last state */
        } else if (_operator.getRawButton(5)) {
            powerCellState = PCState.Spit;
            armState = ArmState.Up;
        } else {
            powerCellState = PCState.Wait;
        }

        /** If we press shoot, try to shoot */
        if (_operator.getRawButton(8)) {
            shooterState = ShooterState.Shooting;
        } else {
            /**
            * If we don't have the shoot button pressed, we need to be either off or prepare shoot First
            * check if we pressed a button to go into either state
            */
            if (_operator.getRawButton(10)) {
                shooterState = ShooterState.Off;
            } else if (_operator.getRawButton(1)) {
                shooterState = ShooterState.PrepareShoot;
            } else {
                /**
                * If neither is pressed, check what state we're in If we're shooting, transition to
                * prepareshoot Otherwise just stay whatever we are.
                */
                if (shooterState == ShooterState.Shooting) {
                    shooterState = ShooterState.PrepareShoot;
                }
            }
        }

        if (routine != AutonState.AutoChanged) {
            if (_driver.getPOV() == 90) {
                routine = AutonState.NextAuto;
            } else if (_driver.getPOV() == 270) {
                routine = AutonState.PreviousAuto;
            }
        } else if (_driver.getPOV() == -1) {
            routine = AutonState.ButtonReleased;
        }

        if (_driver.getPOV() == 180) {
            clearSensors = true;
        } else {
            clearSensors = false;
        }
    }
}
