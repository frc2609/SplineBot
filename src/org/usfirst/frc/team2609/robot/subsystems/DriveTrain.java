package org.usfirst.frc.team2609.robot.subsystems;

import org.usfirst.frc.team2609.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void resetEncoders(){
        RobotMap.driveLeft1.setEncPosition(0); // Set wherever the encoder is to position 0
        RobotMap.driveRight1.setEncPosition(0); // Set wherever the encoder is to position 0
    }
    public void arcadeDrive(double X, double Y, double deadZone){
    	if (Math.abs(X)<deadZone){
        	X = 0;
        }
        if (Math.abs(Y)<deadZone){
        	Y = 0;
        }
        double leftOutput;
        double rightOutput;
        if (Y > 0) {
            if (X > 0.0) {
                leftOutput = Y - X;
                rightOutput = Math.max(Y, X);
            } else {
                leftOutput = Math.max(Y, -X);
                rightOutput = Y + X;
            }
        } else{
            if (X > 0.0) {
                leftOutput = -Math.max(-Y, X);
                rightOutput = Y + X;
            } else {
                leftOutput = Y - X;
                rightOutput = -Math.max(-Y, -X);
            }
        }
        this.tankDrive(rightOutput, leftOutput);
    }
    public void tankDrive(double rightOutput, double leftOutput){
    	// TODO: Make sure that the talons are in voltage mode!
    	RobotMap.driveLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveLeft1.set(leftOutput);
    	RobotMap.driveRight1.set(rightOutput);
    }
}

