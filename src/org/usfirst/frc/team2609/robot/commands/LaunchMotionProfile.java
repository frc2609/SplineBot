
package org.usfirst.frc.team2609.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;
import org.usfirst.frc.team2609.robot.subsystems.MotionProfileSubsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public class LaunchMotionProfile extends Command {
    public LaunchMotionProfile() {
        // Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap._MotionPLeft.reset();
//    	RobotMap._MotionPRight.reset();
    	RobotMap.drivetrainMPActive = true;
    	RobotMap._MotionPLeft.startMotionProfile();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	RobotMap.drivetrainMPActive = false;
    	RobotMap._MotionPLeft.reset();
//    	RobotMap._MotionPRight.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
