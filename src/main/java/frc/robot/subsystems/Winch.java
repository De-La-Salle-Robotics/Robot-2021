package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import frc.robot.utils.JoystickVals;

public class Winch {
    private BaseMotorController _winch;
    
    public Winch(BaseMotorController winch){
        this._winch = winch;
    }

    public void HookControl(JoystickVals joysticks){
        double winchPower = 0;

        if (joysticks.winchDown){
            winchPower = 1.0;
        }
        else{
            winchPower = 0;
        }
        _winch.set(ControlMode.PercentOutput, winchPower);

        
    }
    

}

























