package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import frc.robot.utils.JoystickVals;

/* Add comments to the code so it details what it's being used for */
public class Arm {
    public BaseMotorController arm;

    public Arm (BaseMotorController arm){
        this.arm = arm;
    }

    public void armControl(JoystickVals joysticks){
        double armpower = 0;
        
        /* Also, for if statements, make it concise like this */
        /* The style may change, because I also like different curly brace options, but for now follow this style */
        if (joysticks.armUp){
            armpower = 0.5;
        } else if (joysticks.armDown){
            armpower = -0.5;
        } else {
            armpower = 0; 
        }
        arm.set(ControlMode.PercentOutput, armpower);
    }

}