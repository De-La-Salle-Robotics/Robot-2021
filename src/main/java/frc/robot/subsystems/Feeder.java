package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;

public class Feeder {
    public BaseMotorController feeder;

    public Feeder (BaseMotorController feeder){
        this.feeder = feeder;
    }

    public void feederControl(RobotState joysticks){
        double feederpower = 0;
        
        if (joysticks.powerCellState == PCState.Shoot){
            feederpower = 1;
        } else {
            feederpower = 0; 
        }
        feeder.set(ControlMode.PercentOutput, feederpower);
    }

}
