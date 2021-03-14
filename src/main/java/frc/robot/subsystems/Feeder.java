package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;

public class Feeder {
    public TalonFX feeder;

    public Feeder(TalonFX feeder) {
        this.feeder = feeder;
    }

    public void feederControl(RobotState joysticks) {
        double feederpower = 0;

        if (joysticks.powerCellState == PCState.Gulp || joysticks.powerCellState == PCState.Discharge) {
            feederpower = 0.15;
        } else if (joysticks.powerCellState == PCState.Spit) {
            feederpower = -0.15;
        } else {
            feederpower = 0;
        }
        feeder.set(ControlMode.PercentOutput, feederpower);
    }
}
