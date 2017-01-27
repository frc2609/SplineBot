package org.usfirst.frc.team2609.robot;

import org.usfirst.frc.team2609.robot.subsystems.MotionProfileSubsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	public static CANTalon driveRight1;
	public static CANTalon driveRight2;
	public static CANTalon driveLeft1;
	public static CANTalon driveLeft2;
	public static double[][] leftPath;
	public static double[][] rightPath;
	public static boolean drivetrainMPActive;
	public static MotionProfileSubsystem _MotionPLeft;
	public static MotionProfileSubsystem _MotionPRight;
	
	
    public static void init(){
		// DONT DEFINE THE OBJECT TYPE HERE!!1111!ONE ex. Victor  driveVictorLeft1 = new Victor(0);
    	driveRight1 = new CANTalon(1);
    	driveRight2 = new CANTalon(2);
    	driveLeft1 = new CANTalon(3);
    	driveLeft2 = new CANTalon(4);
    	
    	driveRight2.changeControlMode(TalonControlMode.Follower);
    	driveLeft2.changeControlMode(TalonControlMode.Follower);
    	driveRight2.set(1);
    	driveLeft2.set(3);
    	
    	driveRight1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	driveRight1.configEncoderCodesPerRev(159); // converts "revolutions" into feet
    	driveRight1.reverseOutput(true);
    	driveRight1.reverseSensor(false);
    	driveLeft1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	driveLeft1.configEncoderCodesPerRev(159); // converts "revolutions" into feet
    	driveLeft1.reverseSensor(true);
    	
    	
    	
    }
}
