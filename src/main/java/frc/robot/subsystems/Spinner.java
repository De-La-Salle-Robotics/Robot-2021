package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;

public class Spinner{
    private BaseMotorController spinner;

    public Spinner(BaseMotorController spinner){
        this.spinner = spinner;
    }
    public void spinnerControl(RobotState joysticks){
        double spinnerpower = 0;

        /* This code handles the spinner for index */
        if (joysticks.powerCellState == PCState.Suck ||
            joysticks.powerCellState == PCState.Gulp){
            spinnerpower = -0.18;
        }
        else if (joysticks.powerCellState == RobotState.PCState.Blow){
            spinnerpower = 0.18;
        }
        else{
            spinnerpower = 0;
        }
        
        spinner.set(ControlMode.PercentOutput, spinnerpower); /* Output the spinner */
    }
}
