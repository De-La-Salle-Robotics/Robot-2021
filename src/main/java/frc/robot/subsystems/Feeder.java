package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;

public class Feeder {
    public TalonFX feeder;

    public Feeder (TalonFX feeder){
        this.feeder = feeder;
    }

    public void feederControl(RobotState joysticks){
        double feederpower = 0;
        
        if (joysticks.powerCellState == PCState.Gulp)
        if (joysticks.powerCellState == PCState.Fire){
            feederpower = 1;
        } 
        else if (joysticks.powerCellState == PCState.Spit){
            feederpower = -0.75;
        }
            else {
            feederpower = 0; 
        }
        feeder.set(ControlMode.PercentOutput, feederpower);
    }

}
