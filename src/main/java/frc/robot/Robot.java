/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.FortKnox.FortKnoxState;

/**
* The VM is configured to automatically run this class, and to call the functions corresponding to
* each mode, as described in the TimedRobot documentation. If you change the name of this class or
* the package after creating this project, you must also update the build.gradle file in the
* project.
*/
public class Robot extends TimedRobot {
    private FortKnox fortknox;
    /**
    * This function is run when the robot is first started up and should be used for any
    * initialization code.
    */
    @Override
    public void robotInit() {
        this.fortknox = new FortKnox();
    }

    @Override
    public void robotPeriodic() {
        fortknox.periodicTasks();
    }

    @Override
    public void autonomousInit() {
        fortknox.changeState(FortKnoxState.Autonomous);
    }

    @Override
    public void teleopInit() {
        fortknox.changeState(FortKnoxState.Teleoperated);
    }

    @Override
    public void disabledInit() {
        fortknox.changeState(FortKnoxState.Disabled);
    }

    @Override
    public void testInit() {
        fortknox.changeState(FortKnoxState.Test);
    }
}
