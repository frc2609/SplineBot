package org.usfirst.frc.team2609.robot.commands;

import java.io.File;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class GearAutonSpline extends Command {
	double time;
    public GearAutonSpline() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
	protected void execute() {
		time = Timer.getFPGATimestamp();
        Waypoint[] points = new Waypoint[] {
                new Waypoint(-0.6, 2.89, Pathfinder.d2r(-60)),
                new Waypoint(0, 0, 0)
        };
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH, 0.01, 1.7, 2.0, 60.0);
		Trajectory trajectory = Pathfinder.generate(points, config);
		TankModifier modifier = new TankModifier(trajectory).modify(0.7);
		File rightFile = new File("/home/lvuser/Right.csv");
		Pathfinder.writeToCSV(rightFile, modifier.getRightTrajectory());
		File leftFile = new File("/home/lvuser/Left.csv");
		Pathfinder.writeToCSV(leftFile, modifier.getLeftTrajectory());
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println(time-Timer.getFPGATimestamp());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
