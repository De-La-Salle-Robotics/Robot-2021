package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {
    NetworkTable table;

    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;

    double targetX;
    double targetY;
    double targetArea;

    public class LimelightTarget {
        public double x;
        public double y;
        public double area;

        public LimelightTarget(double x, double y, double area) {
            this.x = x;
            this.y = y;
            this.area = area;
        }

        @Override
        public String toString() {
            return "Target: " + x + " " + y + " " + ". Area: " + area;
        }
    }

    public Limelight() {
        /* Just assuming that this is correct */
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
    }

    public void update() {

        // read values periodically
        targetX = tx.getDouble(0.0);
        targetY = ty.getDouble(0.0);
        targetArea = ta.getDouble(0.0);

        // post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", targetX);
        SmartDashboard.putNumber("LimelightY", targetY);
        SmartDashboard.putNumber("LimelightArea", targetArea);
    }

    public LimelightTarget getTarget() {
        return new LimelightTarget(targetX, targetY, targetArea);
    }
}
