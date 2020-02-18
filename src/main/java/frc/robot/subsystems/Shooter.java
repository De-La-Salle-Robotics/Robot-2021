package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;

public class Shooter {
    public BaseMotorController shooter;

    public Shooter (BaseMotorController shooter){
        this.shooter = shooter;
    }

    public void shooterControl(RobotState joysticks){
        double shooterpower = 0;
        
        if (joysticks.powerCellState == PCState.Shoot){
            shooterpower = 0.5;
        } else {
            shooterpower = 0; 
        }
        shooter.set(ControlMode.PercentOutput, shooterpower);
    }

}