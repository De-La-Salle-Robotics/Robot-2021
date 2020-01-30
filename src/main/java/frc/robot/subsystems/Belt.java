package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.utils.JoystickVals;

public class Belt{
    private BaseMotorController belt;
    private BaseMotorController shooter;

    public Belt(BaseMotorController belt, BaseMotorController shooter){
        this.belt = belt;
        this.shooter = shooter;
    }
    public void beltControl(JoystickVals joysticks){
        double beltpower = 0;
        double shooterpower = 0;

        /* This code handles the conveyor belt */
        if (joysticks.in){
            beltpower = 0.5;
        }
        else if (joysticks.out){
            beltpower = -0.5;
        }
        else{
            beltpower = 0;
        }

        /* This code handles the shooter */
        if (joysticks.shooterFire){
            shooterpower = 0.5;
        } else if (joysticks.shooterReload){
            shooterpower = -0.5;
        } else {
            shooterpower = 0; 
        }
        
        belt.set(ControlMode.PercentOutput, beltpower); /* Output the conveyor belt */
        shooter.set(ControlMode.PercentOutput, shooterpower); /* Output the shooter */
    }
}
