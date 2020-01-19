package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.utils.JoystickVals;

public class Belt{
    public BaseMotorController belt;

    public Belt(BaseMotorController belt){
        this.belt = belt;
    }
    public void beltControl(JoystickVals joysticks){
        double beltpower = 0;

        if (joysticks.in){
            beltpower = 0.5;
        }
        else if (joysticks.out){
            beltpower = -0.5;
        }
        else{
            beltpower = 0;
        }
        belt.set(ControlMode.PercentOutput, beltpower);
    }

}
