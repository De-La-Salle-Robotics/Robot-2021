package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/* Add comments to the code so it details what it's being used for */
public class Arm {
    public VictorSPX arm;

    public Arm (VictorSPX arm){
        this.arm = arm;
    }

    public void ArmControl(boolean armUp, boolean armDown){
        double armpower = 0;
        
        /* Also, for if statements, make it concise like this */
        /* The style may change, because I also like different curly brace options, but for now follow this style */
        if (armUp){
            armpower = 0.5;
        } else if (armDown){
            armpower = -0.5;
        } else {
            armpower = 0; 
        }
        arm.set(ControlMode.PercentOutput, armpower);
    }

}