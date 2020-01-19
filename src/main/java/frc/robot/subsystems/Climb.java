package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import frc.robot.utils.JoystickVals;

public class Climb {
    public BaseMotorController climb;

    public Climb(BaseMotorController climb) {
        this.climb = climb;
    }
public void  climbControl(JoystickVals joysticks){
    double climbpower = 0;

    if(joysticks.raise){
        climbpower = 1.0;
    }
    else if(joysticks.lower){
        climbpower = -1.0;
    }
    else{
        climbpower = 0;
    }
    climb.set(ControlMode.PercentOutput, climbpower);

    }

}
