package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import frc.robot.utils.JoystickVals;

public class Shooter {
    public BaseMotorController shooter;

    public Shooter (BaseMotorController shooter){
        this.shooter = shooter;
    }

    public void shooterControl(JoystickVals joysticks){
        double shooterpower = 0;
        
        if (joysticks.shooterFire){
            shooterpower = 0.5;
        } else if (joysticks.shooterReload){
            shooterpower = -0.5;
        } else {
            shooterpower = 0; 
        }
        shooter.set(ControlMode.PercentOutput, shooterpower);
    }

}