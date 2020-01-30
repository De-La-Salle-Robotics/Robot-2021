package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.utils.JoystickVals;

public class Arm {
    public BaseMotorController arm;

    public Arm(BaseMotorController arm){
        this.arm = arm;
    }

    public void armControl(JoystickVals joysticks){
        double armpower = 0;

        if (joysticks.armUp){
            armpower = 0.5;
        }
        else if (joysticks.armDown){
            armpower = -0.5;
        }
        else {
            armpower = 0;
        }
        arm.set(ControlMode.PercentOutput, armpower);
    }

}
