package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class RobotHardware {
    public static TalonSRX leftMaster;
    public static TalonSRX leftSlave;
    public static TalonSRX rightMaster;
    public static TalonSRX rightSlave;

    public static TalonSRX arm;

    static {
        leftMaster = new TalonSRX(1);
        leftSlave = new TalonSRX(2);

    }
}