package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;
import frc.robot.utils.RobotState.ShooterState;

public class Flywheel {
    private final double flywheelThreshold = 20450;
    public TalonFX flywheel;
    public TalonFX feeder;

    public Flywheel(TalonFX flywheel, TalonFX feeder) {
        this.flywheel = flywheel;
        this.feeder = feeder;
    }

    public void flywheelControl(RobotState joysticks) {
        double flywheelpower = 0;
        double feederpower = 0;

        /**
        * The interaction between flywheel and feeder is complex We want the feeder to operate
        * independent of flywheel when we aren't trying to shoot, or if we want to override the
        * autofeed, but otherwise it's under the flywheel's control.
        */
        if (joysticks.shooterState == ShooterState.PrepareShoot
                || joysticks.shooterState == ShooterState.Shooting) {
            flywheelpower = 1;
        } else {
            flywheelpower = 0;
        }

        if (joysticks.powerCellState == PCState.Index) {
            feederpower = 0.35;
        } else if (joysticks.powerCellState == PCState.Spit) {
            feederpower = -0.15;
        } else if (joysticks.shooterState == ShooterState.Shooting
                && flywheel.getSelectedSensorVelocity() > flywheelThreshold) {
            feederpower = 0.35;
        } else {
            feederpower = 0;
        }

        feeder.set(ControlMode.PercentOutput, feederpower);
        flywheel.set(ControlMode.PercentOutput, flywheelpower);
    }
}
