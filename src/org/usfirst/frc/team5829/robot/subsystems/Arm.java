package org.usfirst.frc.team5829.robot.subsystems;

import org.usfirst.frc.team5829.robot.RobotMap;
import org.usfirst.frc.team5829.robot.commands.ArmMove;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Arm extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public static Spark liftMotor1 = new Spark(RobotMap.liftMotor1);
	public static TalonSRX liftMotor2 = new TalonSRX(RobotMap.liftMotor2);
	public static TalonSRX liftMotor3 = new TalonSRX(RobotMap.liftMotor3);
	public static final double armSpeed = 0.75;
	public static DoubleSolenoid armMove = new DoubleSolenoid(RobotMap.arm_down,RobotMap.arm_up);
	public static DoubleSolenoid bikeBreak = new DoubleSolenoid(RobotMap.breakClose, RobotMap.breakOpen);
	public static DigitalInput bumper = new DigitalInput(RobotMap.bumper);
	
    public void initDefaultCommand(){
    	setDefaultCommand(new ArmMove(0, 0));
    }
    
    public static void armMoveMotor(int upOrDown) {
    	if(upOrDown == -1){
    		bikeBreak.set(DoubleSolenoid.Value.kForward);
    		liftMotor1.set(armSpeed);
    		liftMotor2.set(ControlMode.PercentOutput, armSpeed);
    		liftMotor3.set(ControlMode.PercentOutput, armSpeed);
    	}else if(upOrDown == 1){
    		bikeBreak.set(DoubleSolenoid.Value.kForward);
    		liftMotor1.set(-armSpeed);
    		liftMotor2.set(ControlMode.PercentOutput, -armSpeed);
    		liftMotor3.set(ControlMode.PercentOutput, -armSpeed);
    	}else{
    		bikeBreak.set(DoubleSolenoid.Value.kReverse);
    		liftMotor1.set(0);
    		liftMotor2.set(ControlMode.PercentOutput, 0);
    		liftMotor3.set(ControlMode.PercentOutput, 0);    		
    	}
    }
    public static void armMovePiston(int upOrDown){
    	if(upOrDown == 0)
    		armMove.set(DoubleSolenoid.Value.kForward);
    	if(upOrDown == 1)
    		armMove.set(DoubleSolenoid.Value.kReverse);
    }
    
    public static boolean getBumperValue()
    {
    	if(bumper.get() == false) {
    		return false;
    	}
    	else
    		return true;
    }
    
   /* public void armMoveMotor(int upOrDown) {
    	leftArm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    }*/
}

