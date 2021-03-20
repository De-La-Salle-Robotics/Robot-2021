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
        /** Index a single ball */
        SingleIndex
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

    public enum ShooterSpeed {
        GreenZone,
        YellowZone,
        BlueZone,
        RedZone
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
        AlignToTarget,
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
    public ShooterSpeed shooterSpeed;

    public boolean clearSensors;

    private Joystick _driver;
    private Joystick _operator;

    private class PowerCellStateButton {
        public int buttonNumber;
        public PCState pState;
        public ArmState aState;

        public PowerCellStateButton(int b, PCState p, ArmState a) {
            buttonNumber = b;
            pState = p;
            aState = a;
        }
    }

    private final PowerCellStateButton[] powerCellStateButtonList =
            new PowerCellStateButton[] {
                /* Arm Up */
                new PowerCellStateButton(2, PCState.Wait, ArmState.Up),
                /* Blow */
                new PowerCellStateButton(3, PCState.Blow, ArmState.Up),
                /* Arm Down */
                new PowerCellStateButton(4, PCState.Wait, ArmState.Down),
                /* Spit */
                new PowerCellStateButton(5, PCState.Spit, ArmState.Up),
                /* Continuous Index */
                new PowerCellStateButton(6, PCState.Index, null),
                /* Suck */
                new PowerCellStateButton(7, PCState.Suck, ArmState.Down),
                /* Single Index */
                new PowerCellStateButton(-1, PCState.SingleIndex, null)
            };
    private final PowerCellStateButton defaultState = new PowerCellStateButton(0, PCState.Wait, null);

    public RobotState(Joystick driver, Joystick operator) {
        _driver = driver;
        _operator = operator;

        /* Initialize states */
        driveTrainState = new DTStruct();
        powerCellState = PCState.Wait;
        shooterState = ShooterState.Off;
        routine = AutonState.AutoChanged;
        shooterSpeed = ShooterSpeed.RedZone;

        clearSensors = false;
    }

    public void getJoystickValues() {
        /* Drive base */
        double ratio;
        if (_driver.getRawButton(6)) {
            ratio = 0.2;
        } else {
            ratio = 0.4;
        }

        double throt = -_driver.getRawAxis(1); /* Throttle is negated */
        double wheel = _driver.getRawAxis(4) * ratio;
        double leftSide = throt + wheel;
        double rightSide = throt - wheel;

        if (Math.abs(leftSide) > 1) {
            rightSide /= Math.abs(leftSide);
            leftSide /= Math.abs(leftSide);
        }
        if (Math.abs(rightSide) > 1) {
            leftSide /= Math.abs(rightSide);
            rightSide /= Math.abs(rightSide);
        }

        if (_driver.getRawButton(5)) {
            driveTrainState.set(DriveTrainState.AlignToTarget, 0, 0);
        } else {
            driveTrainState.set(DriveTrainState.PercentOut, leftSide, rightSide);
        }

        /* Shooter Speed Handler */
        switch (_operator.getPOV()) {
            case 90:
                shooterSpeed = ShooterSpeed.GreenZone;
                break;
            case 180:
                shooterSpeed = ShooterSpeed.YellowZone;
                break;
            case 270:
                shooterSpeed = ShooterSpeed.BlueZone;
                break;
            case 0:
                shooterSpeed = ShooterSpeed.RedZone;
                break;
        }

        /* Powercell State Handler */
        powerCellState = defaultState.pState;
        /* Assume we're in default state for now */
        if (defaultState.aState != null) {
            armState = defaultState.aState;
        }
        /* Check every possible we can be in, if we pressed the button go into that state */
        for (PowerCellStateButton stateButton : powerCellStateButtonList) {
            if (stateButton.buttonNumber > 0 && _operator.getRawButton(stateButton.buttonNumber)) {
                powerCellState = stateButton.pState;
                if (stateButton.aState != null) {
                    armState = stateButton.aState;
                }
                break;
            }
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
