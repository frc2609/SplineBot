package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team2609.robot.commands.GearAutonSpline;
import org.usfirst.frc.team2609.robot.commands.LaunchMP;
import org.usfirst.frc.team2609.robot.commands.LaunchMotionProfile;
import org.usfirst.frc.team2609.robot.commands.MotionProfileEStop;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	public static Joystick driveStick;
	public static JoystickButton button1;
	public static JoystickButton button2;
	public static JoystickButton button3;
	public static JoystickButton button5;
	public OI(){
		driveStick = new Joystick(0);
		button1 = new JoystickButton(driveStick, 1);
		button1.whenPressed(new GearAutonSpline());
		button2 = new JoystickButton(driveStick, 2);
		button2.whenPressed(new LaunchMotionProfile());
		button3 = new JoystickButton(driveStick, 3);
		button3.whenPressed(new MotionProfileEStop());
		button5 = new JoystickButton(driveStick, 5);
		button5.whenPressed(new LaunchMP());
	}
}

