package frc.robot.autonomous;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;

public class AutonomousHardware {
    public BaseTalon leftSide;
    public BaseTalon rightSide;
    public BaseMotorController belt;
    public BaseMotorController shooter;

    public AutonomousHardware(BaseTalon leftSide, 
                            BaseTalon rightSide, 
                            BaseMotorController belt, 
                            BaseMotorController shooter) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.belt = belt;
        this.shooter = shooter;
    }
}