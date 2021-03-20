package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.time.StopWatch;
import frc.robot.subsystems.Limelight.LimelightTarget;
import frc.robot.utils.RobotState;

public class Drivetrain {
    /* Turn pigeon native units to an angle */
    private final double NATIVE_UNITS_TO_ANGLE = 8192.0 / 360.0;
    private final double OUTPUT_THRESHOLD = 0.01;
    private final double LIMELIGHT_DELAY = 0.2; // !< Observed about a 200ms delay
    private final double GOOD_ANGLE = 0.4; // !< We're good if we're within 1 degree
    private final double LOW_POWER_TIMEOUT = 0.3;

    private TalonFX _leftMaster;
    private TalonFX _rightMaster;
    private PigeonIMU _pidgey;
    private Limelight _limelight;

    private StopWatch _stopWatch;
    private StopWatch _lowpowerStopWatch;

    private enum AlignState {
        SetAngle,
        ImuPID,
        Check
    }

    private AlignState _currentAlignState;
    private double _angleToTurn;
    private double _positionToStay;

    public Drivetrain(
            TalonFX leftMaster, TalonFX rightMaster, PigeonIMU pigeon, Limelight limelight) {
        _leftMaster = leftMaster;
        _rightMaster = rightMaster;
        _pidgey = pigeon;
        _limelight = limelight;
        _currentAlignState = AlignState.SetAngle;
        _angleToTurn = 0;
        _stopWatch = new StopWatch();
        _lowpowerStopWatch = new StopWatch();
    }

    public void operate(RobotState joysticks) {
        /* Check if we should reset sensors */
        if (joysticks.clearSensors) {
            _leftMaster.getSensorCollection().setIntegratedSensorPosition(0, 0);
            _rightMaster.getSensorCollection().setIntegratedSensorPosition(0, 0);
            _pidgey.setYaw(0);
        }

        switch (joysticks.driveTrainState.state) {
            case PercentOut:
                _leftMaster.set(TalonFXControlMode.PercentOutput, joysticks.driveTrainState.leftSide);
                _rightMaster.set(TalonFXControlMode.PercentOutput, joysticks.driveTrainState.rightSide);
                break;
            case Position:
                break;
            case MotionMagic:
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
                switch (_currentAlignState) {
                    case SetAngle:
                        /* Find the position to stay at, and the angle to turn toward */
                        _positionToStay = _rightMaster.getSelectedSensorPosition(0);
                        _angleToTurn =
                            _rightMaster.getSelectedSensorPosition(1) - (target.x * NATIVE_UNITS_TO_ANGLE);
                        _currentAlignState = AlignState.ImuPID;
                        _lowpowerStopWatch.start();
                        break;
                    case ImuPID:
                        _leftMaster.follow(_rightMaster, FollowerType.AuxOutput1);
                        _rightMaster.set(
                                TalonFXControlMode.Position, _positionToStay, DemandType.AuxPID, _angleToTurn);
                        /* If our output percent is small, we're probably there */
                        System.out.println(_lowpowerStopWatch.getDuration());
                        if (Math.abs(_rightMaster.getMotorOutputPercent()) < OUTPUT_THRESHOLD) {
                            if(_lowpowerStopWatch.getDuration() > LOW_POWER_TIMEOUT) {
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
                            if (Math.abs(target.x) < GOOD_ANGLE) {
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
