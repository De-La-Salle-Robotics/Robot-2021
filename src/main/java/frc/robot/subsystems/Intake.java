package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import frc.robot.utils.JoystickVals;

public class Intake {
    public BaseMotorController intake;

    public Intake(BaseMotorController intake){
        this.intake = intake;
    }

    public void intakeControl(JoystickVals joysticks){
        double intakepower = 0; 

        if (joysticks.collectorSuck){
            intakepower = 1.0;
        }
        else if (joysticks.collectorBlow){
            intakepower = -1.0;
        }
        else{
            intakepower = 0;
        }
        intake.set(ControlMode.PercentOutput, intakepower);
    }

}
