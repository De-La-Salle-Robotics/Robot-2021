package frc.robot.subsystems;

import com.ctre.phoenix.mototcontrol.NeutralMode;
import com.ctre.phoneix.motorcontrol.TalonFXInvertType;
import com.ctre.phoneix.motorcontrol.ControlMode;
import com.ctre.phoniex.motorcontrol.DemandType;
import com.ctre.phoneix.motorcontrol.can.TalonFX; 

import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;

public class Flywheel {
  public TalonFX flywheel;
  
  public Flywheel (TalonFX flywheel){
      this.flywheel = flywheel;
     
     }
     
      public void flywheelControl(RobotState joysticks){
        double flywheelpower = 0;
        
        if (joysticks.powerCellState == PCState.Shoot){
            flywheelpower = 1;
            } else {
             flywheelpower = 0;
             } 
             flywheel.set(ControlMode.PercentOutput, flywheelpower);
           }
           
          }
