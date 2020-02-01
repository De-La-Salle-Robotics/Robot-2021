package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.Joystick;

public class RobotMap {
    /* Drivetrain */
    public static BaseTalon leftMaster;
    public static BaseMotorController leftSlave;
    public static BaseTalon rightMaster;
    public static BaseMotorController rightSlave;

    /* Arm and Collector */
    public static BaseMotorController arm;
    public static BaseMotorController intake;
    /* Conveyor Belt */
    public static BaseMotorController belt;
    public static BaseMotorController shooter;
    /* Climber */
    public static BaseMotorController climb;
    public static BaseMotorController winch;

    public static Joystick driverJoystick;
    public static Joystick operatorJoystick;
    static {
        leftMaster = new TalonSRX(1);
        leftSlave = new VictorSPX(1);
        rightMaster = new TalonSRX(2);
        rightSlave = new VictorSPX(2);

        arm = new TalonSRX(3);
        intake = new VictorSPX(3);

        belt = new VictorSPX(4);
        shooter = new VictorSPX(5);

        climb = new VictorSPX(6);
        winch = new VictorSPX(7);

        driverJoystick = new Joystick(0);
        operatorJoystick = new Joystick(1);
    }
}