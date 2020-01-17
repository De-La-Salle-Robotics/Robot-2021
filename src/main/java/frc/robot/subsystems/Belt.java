package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Belt{
    public VictorSPX belt;

    public Belt(VictorSPX belt){
        this.belt = belt;
    }
    public Belt(Boolean in, Boolean out){
        double beltpower = 0;

        if (in){
            beltpower = 0.5;
        }
        else if (out){
            beltpower = -0.5;
        }
        else{
            beltpower = 0;
        }
        belt.set(ControlMode.PercentOutput, beltpower);
    }

}
