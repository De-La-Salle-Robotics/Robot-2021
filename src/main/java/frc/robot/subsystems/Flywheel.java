package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.time.StopWatch;
import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;
import frc.robot.utils.RobotState.ShooterState;

public class Flywheel {
    private final double flywheelThreshold = 20450;
    private final double indexTimeLength = 0.0;
    private final double indexPowerForward = 0.35;
    private final double indexPowerReverse = -0.15;
    private StopWatch stopWatch;
    private PCState lastState;

    public TalonFX flywheel;
    public TalonFX feeder;

    public Flywheel(TalonFX flywheel, TalonFX feeder) {
        this.flywheel = flywheel;
        this.feeder = feeder;
        lastState = PCState.Wait;
        stopWatch = new StopWatch();
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

        /* If we are single-indexing, check to see if we just started single-indexing */
        if (lastState != PCState.SingleIndex && joysticks.powerCellState == PCState.SingleIndex) {
            /* We just started, let's clear the timer and wait */
            stopWatch.start();
            feederpower = indexPowerForward;
        } else if (joysticks.powerCellState == PCState.SingleIndex) {
            /* We're currently single-indexing */
            /* We need to wait for indexTimeLength time before we stop */
            if (stopWatch.getDuration() > indexTimeLength) {
                /* Still run, we aren't done yet */
                feederpower = indexPowerForward;
            } else {
                /* It's been enough time, let's stop */
                feederpower = 0;
            }
        } else if (joysticks.powerCellState == PCState.Index) {
            feederpower = indexPowerForward;
        } else if (joysticks.powerCellState == PCState.Spit) {
            feederpower = indexPowerReverse;
        } else if (joysticks.shooterState == ShooterState.Shooting
                && flywheel.getSelectedSensorVelocity() > flywheelThreshold) {
            feederpower = indexPowerForward;
        } else {
            feederpower = 0;
        }
        lastState = joysticks.powerCellState;

        feeder.set(ControlMode.PercentOutput, feederpower);
        flywheel.set(ControlMode.PercentOutput, flywheelpower);
    }
}
