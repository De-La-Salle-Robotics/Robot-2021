package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Intake {
    public VictorSPX intake;

    public Intake(VictorSPX intake){
        this.intake = intake;
    }

    public Intake (boolean in, boolean out){
        double intakepower = 0; 

        if (in){
            intakepower = 1.0;
        }
        else if (out){
            intakepower = -1.0;
        }
        else{
            intakepower = 0;
        }
        intake.set(ControlMode.PercentOutput, intakepower);
    }

}
