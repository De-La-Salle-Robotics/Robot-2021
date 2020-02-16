package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.HangState;

public class Climb {
    public BaseMotorController climb;

    public Climb(BaseMotorController climb) {
        this.climb = climb;
    }
public void  climbControl(RobotState joysticks){
    double climbpower = 0;

    if(joysticks.hanger == HangState.Deploy){
        climbpower = 1.0;
    }else if(joysticks.hanger == HangState.Retract){
        climbpower = -1.0;
    }else{
        climbpower = 0;
    }
    climb.set(ControlMode.PercentOutput, climbpower);
    }

}
