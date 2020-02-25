package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;

public class Belt{
    private BaseMotorController belt;

    public Belt(BaseMotorController belt){
        this.belt = belt;
    }
    public void beltControl(RobotState joysticks){
        double beltpower = 0;

        /* This code handles the conveyor belt */
        if (joysticks.powerCellState == PCState.Suck ||
            joysticks.powerCellState == PCState.Shoot){
            beltpower = 1;
        }
        else if (joysticks.powerCellState == RobotState.PCState.Blow){
            beltpower = -0.8;
        }
        else{
            beltpower = 0;
        }
        
        belt.set(ControlMode.PercentOutput, beltpower); /* Output the conveyor belt */
    }
}
