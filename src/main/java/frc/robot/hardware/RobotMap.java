package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

public class RobotMap {
    /* Drivetrain */
    public static TalonFX leftMaster;
    public static TalonFX leftSlave;
    public static TalonFX rightMaster;
    public static TalonFX rightSlave;
    public static PigeonIMU pidgey;

    /* Arm and Collector */
    public static DoubleSolenoid arm;
    public static BaseMotorController intake;
    /* Spinner */
    public static VictorSPX spinner;
    /* Feeder */
    public static TalonFX feeder;
    /* Flywheel */
    public static TalonFX flywheel;


    public static Joystick driverJoystick;
    public static Joystick operatorJoystick;

    public static void initialize() {
        leftMaster = new TalonFX(0);
        leftSlave = new TalonFX(1);
        rightMaster = new TalonFX(2);
        rightSlave = new TalonFX(3);
        pidgey = new PigeonIMU(1);

        arm = new DoubleSolenoid(0, 1);
        intake = new TalonSRX(6);

        spinner = new VictorSPX(4);
        
        feeder = new TalonFX(4);
        flywheel = new TalonFX(5);

        spinner.setInverted(true);
        feeder.setInverted(true);
        flywheel.setInverted(true);

        driverJoystick = new Joystick(0);
        operatorJoystick = new Joystick(1);

        configControllers();
    }

    private static void configControllers() {
        LeftDrivetrainConfigs.configSide(leftMaster, leftSlave);
        RightDrivetrainConfigs.configSide(rightMaster, rightSlave, leftMaster, pidgey);
        ConveyorConfigs.configConveyor(spinner, feeder);
    }
}
