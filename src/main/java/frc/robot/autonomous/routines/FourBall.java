package frc.robot.autonomous.routines;

import frc.robot.autonomous.actions.*;

public class FourBall extends BaseActionRoutine {
    /* Distance, Heading, Time */
    @Override
    protected Action[] getRoutines() {
        return new Action[] {
            new MMDriveCollect(18000, 0, 1.5),
            new MMDriveCollect(-30000, -70, 1.5),
            new MMDrive(-17000, 0, 1.9),
            new MMDrive(-15000, 70, 2.0),
            new Score(4.0),
        };
    }

    public String getName() { return "Four Ball"; }
}