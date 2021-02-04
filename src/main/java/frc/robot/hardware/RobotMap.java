package frc.robot.hardware;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.CANifier;

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
    public static VictorSPX spinnner;
    /* Feeder */
    public static TalonFX feeder;
    /* Adjustable Hood */
    public static VictorSPX hood;
    /* Flywheel */
    public static TalonFX flywheel;
    /* Climber */
    public static VictorSPX climb;
    public static VictorSPX winch;

    public static Joystick driverJoystick;
    public static Joystick operatorJoystick;

    public static CANifier canifier;

    public static void initialize() {
        leftMaster = new TalonFX(1);
        leftSlave = new TalonFX(2);
        rightMaster = new TalonFX(3);
        rightSlave = new TalonFX(4);
        pidgey = new PigeonIMU(1);

        arm = new DoubleSolenoid(0, 1);
        intake = new VictorSPX(6);

        spinner = new VictorSPX(4);
        
        feeder = new TalonFX(5);
        flywheel = new TalonFX(6);

        spinner.setInverted(true);
        feeder.setInverted(true);
        flywheel.setInverted(true);

        climb = new VictorSPX(7);
        winch = new VictorSPX(3);

        driverJoystick = new Joystick(0);
        operatorJoystick = new Joystick(1);

        canifier = new CANifier(0);

        configControllers();
    }

    private static void configControllers() {
        LeftDrivetrainConfigs.configSide(leftMaster, leftSlave);
        RightDrivetrainConfigs.configSide(rightMaster, rightSlave, leftMaster, pidgey);
        HangerConfigs.configHanger(winch, climb, canifier);
        ConveyorConfigs.configConveyor(spinner, feeder);
    }
}
