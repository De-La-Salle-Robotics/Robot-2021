package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;

public class Arm {
    private DoubleSolenoid _arm;
    private Value _armState;

    public Arm(DoubleSolenoid arm) {
        _arm = arm;

        _armState = Value.kOff;
    }

    public void armControl(RobotState joysticks) {
        if (joysticks.powerCellState == PCState.WaitUp || joysticks.powerCellState == PCState.Blow) {
            _armState = Value.kReverse;
        } else if (joysticks.powerCellState == PCState.WaitDown
                || joysticks.powerCellState == PCState.Suck) {
            _armState = Value.kForward;
        }
        _arm.set(_armState);
    }
}
