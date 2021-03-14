package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import frc.robot.utils.RobotState;
import frc.robot.utils.RobotState.PCState;

public class Intake {
    public BaseMotorController intake;

    public Intake(BaseMotorController intake) {
        this.intake = intake;
    }

    public void intakeControl(RobotState joysticks) {
        double intakepower = 0;

        if (joysticks.powerCellState == PCState.Suck) {
            intakepower = -1.0;
        } else if (joysticks.powerCellState == PCState.Blow) {
            intakepower = 1.0;
        } else {
            intakepower = 0;
        }

        intake.set(ControlMode.PercentOutput, intakepower);
    }
}
