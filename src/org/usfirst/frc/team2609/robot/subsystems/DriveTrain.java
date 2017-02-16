package org.usfirst.frc.team2609.robot.subsystems;

import org.usfirst.frc.team2609.loops.Loop;
import org.usfirst.frc.team2609.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	private final Loop mLoop = new Loop() {
		@Override
		public void onStart(){
			
		}
		@Override
		public void onLoop(){
			synchronized (DriveTrain.this){
				if(RobotMap.drivetrainMPActive){
					// Run motion profiler
				}
			}
		}
		@Override
		public void onStop(){
			
		}
	};
	
	public Loop getLooper(){
		return mLoop;
	}
	
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

        if ((Math.abs(X)<deadZone) && (Math.abs(Y)<deadZone)){
        	X = 0;
        	Y = 0;
        }
        /*if (Math.abs(-driveStick.getRawAxis(1))<deadZone){
        	Y = 0;
        }*/
        double leftOutput;
        double rightOutput;
        if (Y > 0) {
            if (X > 0.0) {
                leftOutput = Math.pow(Y, 1) - Math.pow(X, 1);
                rightOutput = Math.max(Math.pow(Y, 1), Math.pow(X, 1));
            } else {
                leftOutput = Math.max(Math.pow(Y, 1), -(Math.pow(X, 1)));
                rightOutput = Math.pow(Y, 1) + (Math.pow(X, 1));
            }
        } else{
            if (X > 0.0) {
                leftOutput = -Math.max(-(Math.pow(Y, 1)), Math.pow(X, 1));
                rightOutput = (Math.pow(Y, 1)) + Math.pow(X, 1);
            } else {
                leftOutput = (Math.pow(Y, 1)) - (Math.pow(X, 1));
                rightOutput = -Math.max(-(Math.pow(Y, 1)), -(Math.pow(X, 1)));
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

