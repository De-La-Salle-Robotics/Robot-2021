package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import frc.robot.utils.JoystickVals;

public class Hook {
    public BaseMotorController Hook;
    
    public Hook(BaseMotorController Hook){
        this.Hook = Hook;
    }

    public void HookControl(JoystickVals joysticks){
        double hookpower = 0;

        if (joysticks.hookUp){
            hookpower = 1.0;
        }
        else{
            hookpower = 0;
        }
        Hook.set(ControlMode.PercentOutput, hookpower);

        
    }
    

}

























