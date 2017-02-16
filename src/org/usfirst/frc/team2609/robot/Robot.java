
package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team2609.enums.DriveSide;
import org.usfirst.frc.team2609.loops.Looper;
import org.usfirst.frc.team2609.robot.commands.GearAutonSpline;
import org.usfirst.frc.team2609.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2609.robot.subsystems.MotionProfileSubsystem;
import org.usfirst.frc.team2609.robot.subsystems.Shifter;
import org.usfirst.frc.team2609.traj.Logger;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Shifter shifter;
	public static OI oi;
	public static DriveTrain _drivetrain = new DriveTrain();
	private Logger logger;
	Looper enabledLooper = new Looper();
    Command autonomousCommand;
    SendableChooser chooser;
    

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
        chooser = new SendableChooser();
        RobotMap.init();
        chooser.addDefault("Default Auto", new GearAutonSpline());
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
    	RobotMap.FRCGyro.calibrate();
    	this.logger = logger.getInstance();
    	
    	try{
    		enabledLooper.register(_drivetrain.getLooper());
    	} catch (Throwable t){
        	System.out.println(t.getMessage());
        	System.out.println(t.getStackTrace());
    	}
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	enabledLooper.stop();
//    	shifter.high();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        this.logger.openFile();
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        this.logger.logAll();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        _drivetrain.resetEncoders();
        RobotMap._MotionPLeft = new MotionProfileSubsystem(RobotMap.driveLeft1, DriveSide.LEFT);
        RobotMap._MotionPRight = new MotionProfileSubsystem(RobotMap.driveRight1, DriveSide.RIGHT);
    	RobotMap._MotionPLeft.reset();
    	RobotMap._MotionPRight.reset();
    	RobotMap.drivetrainMPActive = false;
    	RobotMap.FRCGyro.reset();
//    	shifter.high();
    	
    	this.logger.openFile();
    	
    	SmartDashboard.putNumber("MPGyro", 0);
    	SmartDashboard.putNumber("MPLeft", 0);
    	SmartDashboard.putNumber("MPRight", 0);
    	
    	enabledLooper.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        RobotMap._MotionPRight.control();
        RobotMap._MotionPLeft.control();
//        System.out.println("Left: " + RobotMap.driveLeft1.getPosition());
//        System.out.println("Right: " + RobotMap.driveRight1.getPosition());
        

        this.logger.logAll();
    	SmartDashboard.putNumber("Gyro", RobotMap.FRCGyro.getAngle());
        double controlScale = 0.7;
		double deadZone = 0.1; // Joystick scale factor
        double X = OI.driveStick.getRawAxis(1);
        double Y = OI.driveStick.getRawAxis(0);
        if(!RobotMap.drivetrainMPActive){
        	_drivetrain.arcadeDrive(X*controlScale, Y*controlScale, deadZone);
        	RobotMap._MotionPLeft.reset();
        	RobotMap._MotionPRight.reset();
        }
        else{

        	SmartDashboard.putNumber("MPGyro", RobotMap.FRCGyro.getAngle());
        	SmartDashboard.putNumber("MPLeft", RobotMap.driveLeft1.getPosition());
        	SmartDashboard.putNumber("MPRight", RobotMap.driveRight1.getPosition());
        	
//        	RobotMap.driveLeft1.changeControlMode(TalonControlMode.MotionProfile);
//        	RobotMap.driveRight1.changeControlMode(TalonControlMode.MotionProfile);
//        	CANTalon.SetValueMotionProfile rightSetOutput = RobotMap._MotionPRight.getSetValue();
//            CANTalon.SetValueMotionProfile leftSetOutput = RobotMap._MotionPLeft.getSetValue();
//            RobotMap.driveLeft1.set(leftSetOutput.value);
//            RobotMap.driveRight1.set(rightSetOutput.value);
//            RobotMap._MotionPRight.startMotionProfile();
//            System.out.println("MP Running");
            
        }
//        System.out.println("Left: " + RobotMap.driveLeft1.getEncPosition());
//        System.out.println("Right: " + RobotMap.driveRight1.getEncPosition());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    public static void eStopMP(){
    	RobotMap.MPLeftDisabled = true;
    	RobotMap.MPRightDisabled = true;
    	RobotMap.drivetrainMPActive = false;
    	RobotMap._MotionPLeft.reset();
    	RobotMap._MotionPRight.reset();
    }
}
