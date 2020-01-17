package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class RobotMap {
    public static TalonSRX leftMaster;
    public static TalonSRX leftSlave;
    public static TalonSRX rightMaster;
    public static TalonSRX rightSlave;

    public static VictorSPX arm;

    static {
        leftMaster = new TalonSRX(1);
        leftSlave = new TalonSRX(2);

    }
}