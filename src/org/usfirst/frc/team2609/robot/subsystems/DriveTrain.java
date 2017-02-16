package org.usfirst.frc.team2609.robot.subsystems;

import org.usfirst.frc.team2609.loops.Loop;
import org.usfirst.frc.team2609.robot.RobotMap;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.followers.EncoderFollower;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	EncoderFollower left,right;
	
	private final Loop mLoop = new Loop() {
		@Override
		public void onStart(){
			System.out.println("Starting DriveTrain loop");
		}
		@Override
		public void onLoop(){
			synchronized (DriveTrain.this){
				if(RobotMap.drivetrainMPActive){
					// Run motion profiler
			    	double leftOutput = left.calculate(-RobotMap.driveLeft1.getEncPosition());
			    	double rightOutput = right.calculate(RobotMap.driveRight1.getEncPosition());
			    	
			    	double angleError = (Pathfinder.r2d(left.getHeading()) - RobotMap.ahrs.getYaw());
			    	
			    	double turn = 0.01*Pathfinder.boundHalfDegrees(angleError);
			    	
//			    	leftOutput = leftOutput-turn;
//			    	rightOutput = rightOutput+turn;
			    	
			    	RobotMap.driveLeft1.set(leftOutput);
			    	RobotMap.driveRight1.set(rightOutput);
			    	
			    	if(left.isFinished() && right.isFinished()){
			    		RobotMap.drivetrainMPActive = false;
			    		System.out.println("Both trajectories finished");
			    	}
			    	
				}
			}
		}
		@Override
		public void onStop(){
			System.out.println("Ending DriveTrain loop");
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
    
    public void initMP(){
		if (RobotMap.leftPath.length != 0) { // This is a terrible idea, because I'm no longer using that array, and I will probably remove it soon and wonder why this doesn't work
																		// The point of this is to make sure that the trajectory has been generated.
			left = new EncoderFollower(RobotMap.gearPath.getLeftTrajectory());
			left.configureEncoder(RobotMap.driveLeft1.getEncPosition(), 3840, 6 / 12);
			left.configurePIDVA(0.0, 0, 0, 1 / (15.7937), 0.0);

			right = new EncoderFollower(RobotMap.gearPath.getRightTrajectory());
			right.configureEncoder(RobotMap.driveRight1.getEncPosition(), 3840, 6 / 12);
			right.configurePIDVA(0.0, 0, 0, 1 / (15.7937), 0.0);

			RobotMap.driveLeft1.changeControlMode(TalonControlMode.PercentVbus);
			RobotMap.driveRight1.changeControlMode(TalonControlMode.PercentVbus);
			RobotMap.drivetrainMPActive = true;
		}
    }
    
}

