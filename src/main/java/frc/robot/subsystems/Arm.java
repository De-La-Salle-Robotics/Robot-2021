package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import frc.robot.utils.JoystickVals;

public class Arm {
    private DoubleSolenoid _arm;
    private Value _armState;

    public Arm(DoubleSolenoid arm){
        _arm = arm;

        _armState = Value.kOff;
    }

    public void armControl(JoystickVals joysticks){
        if (joysticks.armUp){
            _armState = Value.kReverse;
        }
        else if (joysticks.armDown){
            _armState = Value.kForward;
        }
        _arm.set(_armState);
    }

}
