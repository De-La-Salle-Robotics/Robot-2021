package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.time.StopWatch;
import frc.robot.subsystems.Limelight.LimelightTarget;
import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.DriveTrainState;

public class Drivetrain {
    /* Turn pigeon native units to an angle */
    private final double NATIVE_UNITS_TO_ANGLE = 8192.0 / 360.0;
    private final double OUTPUT_THRESHOLD = 0.01;
    private final double LIMELIGHT_DELAY = 0.2; // !< Observed about a 200ms delay
    private final double GOOD_ANGLE = 0.4; // !< We're good if we're within 1 degree
    private final double LOW_POWER_TIMEOUT = 0.3;
    private final double RED_TARGET_OFFSET = -0.25;
    private final double BLUE_TARGET_OFFSET = -0.7;
    private final double YELLOW_TARGET_OFFSET = -1.95;
    private final double GREEN_TARGET_OFFSET = 0;

    private TalonFX _leftMaster;
    private TalonFX _rightMaster;
    private TalonFX _leftFollower;
    private TalonFX _rightFollower;
    private PigeonIMU _pidgey;
    private Limelight _limelight;

    private StopWatch _stopWatch;
    private StopWatch _lowpowerStopWatch;
    private StopWatch _coastStopWatch;

    private enum AlignState {
        SetAngle,
        ImuPID,
        Check
    }

    private AlignState _currentAlignState;
    private double _angleToTurn;
    private double _positionToStay;

    public Drivetrain(
            TalonFX leftMaster,
            TalonFX rightMaster,
            TalonFX leftFollower,
            TalonFX rightFollower,
            PigeonIMU pigeon,
            Limelight limelight) {
        _leftMaster = leftMaster;
        _rightMaster = rightMaster;
        _leftFollower = leftFollower;
        _rightFollower = rightFollower;
        _pidgey = pigeon;
        _limelight = limelight;
        _currentAlignState = AlignState.SetAngle;
        _angleToTurn = 0;
        _stopWatch = new StopWatch();
        _lowpowerStopWatch = new StopWatch();
        _coastStopWatch = new StopWatch();
        _coastStopWatch.start();
    }

    public void operate(RobotState joysticks) {
        /* Check if we should reset sensors */
        if (joysticks.clearSensors) {
            _leftMaster.getSensorCollection().setIntegratedSensorPosition(0, 0);
            _rightMaster.getSensorCollection().setIntegratedSensorPosition(0, 0);
            _pidgey.setYaw(0);
        }

        if (joysticks.driveTrainState.state == DriveTrainState.Coast) {
            if (_coastStopWatch.getDuration() > 1) {
                _leftMaster.setNeutralMode(NeutralMode.Coast);
                _rightMaster.setNeutralMode(NeutralMode.Coast);
                _leftFollower.setNeutralMode(NeutralMode.Coast);
                _rightFollower.setNeutralMode(NeutralMode.Coast);
            }
        } else {
            _coastStopWatch.start();
            _leftMaster.setNeutralMode(NeutralMode.Brake);
            _rightMaster.setNeutralMode(NeutralMode.Brake);
            _leftFollower.setNeutralMode(NeutralMode.Brake);
            _rightFollower.setNeutralMode(NeutralMode.Brake);
        }

        switch (joysticks.driveTrainState.state) {
            case PercentOut:
                _leftMaster.set(TalonFXControlMode.PercentOutput, joysticks.driveTrainState.leftSide);
                _rightMaster.set(TalonFXControlMode.PercentOutput, joysticks.driveTrainState.rightSide);
                break;
            case Position:
                break;
            case Coast:
                break;
            case MotionMagic:
                _rightMaster.selectProfileSlot(2, 1);
                _leftMaster.follow(_rightMaster, FollowerType.AuxOutput1);
                _rightMaster.set(
                        TalonFXControlMode.MotionMagic,
                        joysticks.driveTrainState.rightSide,
                        DemandType.AuxPID,
                        joysticks.driveTrainState.leftSide);
                break;
            case AlignToTarget:
                /* We want to turn so that the tx is 0 */
                LimelightTarget target = _limelight.getTarget();

                double target_x = target.x;

                _rightMaster.selectProfileSlot(1, 1);

                switch (joysticks.shooterSpeed) {
                    case RedZone:
                        target_x -= RED_TARGET_OFFSET;
                        break;
                    case BlueZone:
                        target_x -= BLUE_TARGET_OFFSET;
                        break;
                    case YellowZone:
                        target_x -= YELLOW_TARGET_OFFSET;
                        break;
                    case GreenZone:
                        target_x -= GREEN_TARGET_OFFSET;
                        break;
                }

                switch (_currentAlignState) {
                    case SetAngle:
                        /* Find the position to stay at, and the angle to turn toward */
                        _positionToStay = _rightMaster.getSelectedSensorPosition(0);
                        _angleToTurn =
                                _rightMaster.getSelectedSensorPosition(1) - (target_x * NATIVE_UNITS_TO_ANGLE);
                        _currentAlignState = AlignState.ImuPID;
                        _lowpowerStopWatch.start();
                        break;
                    case ImuPID:
                        _leftMaster.follow(_rightMaster, FollowerType.AuxOutput1);
                        _rightMaster.set(TalonFXControlMode.PercentOutput, 0, DemandType.AuxPID, _angleToTurn);
                        /* If our output percent is small, we're probably there */
                        if (Math.abs(_rightMaster.getMotorOutputPercent()) < OUTPUT_THRESHOLD) {
                            if (_lowpowerStopWatch.getDuration() > LOW_POWER_TIMEOUT) {
                                _currentAlignState = AlignState.Check;
                                _stopWatch.start();
                            }
                        } else {
                            _lowpowerStopWatch.start();
                        }
                        break;
                    case Check:
                        /* Wait a bit to combat lag from Limelight */
                        if (_stopWatch.getDuration() > LIMELIGHT_DELAY) {
                            /* Check to see if angle is good */
                            if (Math.abs(target_x) < GOOD_ANGLE) {
                                /* We're good, we can do something */
                            } else {
                                /* Not there yet, do this again */
                                _currentAlignState = AlignState.SetAngle;
                            }
                        }
                        _leftMaster.neutralOutput();
                        _rightMaster.neutralOutput();
                        break;
                }
                break;
        }
    }
}
