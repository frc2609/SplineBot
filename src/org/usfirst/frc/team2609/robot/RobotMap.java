package org.usfirst.frc.team2609.robot;

import org.usfirst.frc.team2609.robot.subsystems.MotionProfileSubsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import jaci.pathfinder.modifiers.TankModifier;

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
	public static boolean MPLeftDisabled;
	public static boolean MPRightDisabled;
	public static MotionProfileSubsystem _MotionPLeft;
	public static MotionProfileSubsystem _MotionPRight;
    public static DoubleSolenoid shifter;
	
	public static AHRS ahrs;
	public static ADXRS450_Gyro FRCGyro;
	
	public static TankModifier gearPath;
	
    public static void init(){
		try {
            ahrs = new AHRS(SPI.Port.kMXP);
            LiveWindow.addSensor("Drivetrain", "AHRS", ahrs);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
		
		// DONT DEFINE THE OBJECT TYPE HERE!!1111!ONE ex. Victor  driveVictorLeft1 = new Victor(0);
    	driveRight1 = new CANTalon(3);
    	driveRight2 = new CANTalon(4);
    	driveLeft1 = new CANTalon(1);
    	driveLeft2 = new CANTalon(2);
    	
    	driveRight2.changeControlMode(TalonControlMode.Follower);
    	driveLeft2.changeControlMode(TalonControlMode.Follower);
    	driveRight2.set(1);
    	driveLeft2.set(3);
    	
    	driveRight1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	driveRight1.configEncoderCodesPerRev(611);
    	driveRight1.reverseSensor(false);
    	driveRight1.reverseOutput(false);
    	driveRight1.setInverted(false);
    	driveLeft1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	driveLeft1.configEncoderCodesPerRev(611);
    	driveLeft1.reverseSensor(false);
    	driveLeft1.reverseOutput(false);
    	driveLeft1.setInverted(true);
    	
    	FRCGyro = new ADXRS450_Gyro();
    	
        shifter = new DoubleSolenoid(0, 1, 0);
    	
    }
}
